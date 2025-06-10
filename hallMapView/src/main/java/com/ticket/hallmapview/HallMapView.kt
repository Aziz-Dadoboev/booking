package com.ticket.hallmapview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.content.ContextCompat

class HallMapView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val defaultWidthDp = 300
    private val defaultHeightDp = 200

    private var mapWidth: Int = 558
    private var mapHeight: Int = 346

    private var seats: List<Seat> = emptyList()

    private val vipDrawable = ContextCompat.getDrawable(context, R.drawable.seat_1)
    private val comfortDrawable = ContextCompat.getDrawable(context, R.drawable.seat_2)
    private val standardDrawable = ContextCompat.getDrawable(context, R.drawable.seat_3)
    private val bookedDrawable = ContextCompat.getDrawable(context, R.drawable.seat_booked)
    private val selectedDrawable = ContextCompat.getDrawable(context, R.drawable.seat_selected)

    private val dm: DisplayMetrics = resources.displayMetrics
    private val scaledDens = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1F, dm)
    else dm.scaledDensity

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
        textSize = 14f * scaledDens
        isAntiAlias = true
    }

    private var scaleFactor = 1f
    private var offsetX = 0f
    private var offsetY = 0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var isDragging = false

    private val selectedSeats = mutableSetOf<Int>()
    var onSeatSelectionChanged: ((Set<Int>) -> Unit)? = null

    private val scaleGestureDetector = ScaleGestureDetector(
        context,
        object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = scaleFactor.coerceIn(1f, 3.0f)
            constrainOffsets()
            invalidate()
            return true
        }
    })

    fun setMapData(
        seats: List<Seat>,
        mapWidth: Int,
        mapHeight: Int
    ) {
        this.seats = seats
        this.mapWidth = mapWidth
        this.mapHeight = mapHeight
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val density = resources.displayMetrics.density

        val desiredWidth = (defaultWidthDp * density).toInt()
        val desiredHeight = (defaultHeightDp * density).toInt()

        val width = resolveSize(desiredWidth, widthMeasureSpec)
        val height = resolveSize(desiredHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.translate(offsetX, offsetY)
        canvas.scale(scaleFactor, scaleFactor)

        val scaleX = width / mapWidth.toFloat()
        val scaleY = height / mapHeight.toFloat()
        val seatSizePx = 24f * resources.displayMetrics.density

        seats.forEach { seat ->
            val cx = seat.left * scaleX
            val cy = seat.top * scaleY

            val left = (cx - seatSizePx / 2).toInt()
            val top = (cy - seatSizePx / 2).toInt()
            val right = (left + seatSizePx).toInt()
            val bottom = (top + seatSizePx).toInt()

            if (seat.seat_type.isBlank()) {
                canvas.drawText(
                    seat.object_title ?: "",
                    cx,
                    cy + textPaint.textSize / 3,
                    textPaint
                )
            } else {
                val seatDrawable = when {
                    selectedSeats.contains(seat.seat_id) -> selectedDrawable
                    seat.seat_type == "VIP" -> vipDrawable
                    seat.seat_type == "COMFORT" -> comfortDrawable
                    seat.seat_type == "STANDARD" -> standardDrawable
                    else -> bookedDrawable
                }

                seatDrawable?.setBounds(left, top, right, bottom)
                seatDrawable?.draw(canvas)

                if (seat.seat_type != "VIP" &&
                    seat.seat_type != "COMFORT" &&
                    seat.seat_type != "STANDARD" &&
                    !selectedSeats.contains(seat.seat_id)) {
                    canvas.drawText(
                        "X",
                        cx,
                        cy,
                        textPaint
                    )
                }
            }
        }
        selectedSeats.forEachIndexed { index, seatId ->
            val seat = seats.find { it.seat_id == seatId }
            if (seat != null) {
                val cx = seat.left * scaleX
                val cy = seat.top * scaleY

                canvas.drawText(
                    "${index + 1}",
                    cx,
                    cy,
                    textPaint
                )
            }
        }

        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)

        if (!scaleGestureDetector.isInProgress) {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchX = event.x
                    lastTouchY = event.y
                    isDragging = true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isDragging) {
                        val dx = event.x - lastTouchX
                        val dy = event.y - lastTouchY
                        offsetX += dx
                        offsetY += dy

                        constrainOffsets()

                        lastTouchX = event.x
                        lastTouchY = event.y
                        invalidate()
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isDragging = false

                    val touchX = (event.x - offsetX) / scaleFactor
                    val touchY = (event.y - offsetY) / scaleFactor

                    val scaleX = width / mapWidth.toFloat()
                    val scaleY = height / mapHeight.toFloat()
                    val seatSizePx = 24f * resources.displayMetrics.density
                    val radius = seatSizePx / 2

                    seats.forEach { seat ->
                        val cx = seat.left * scaleX
                        val cy = seat.top * scaleY

                        val dx = touchX - cx
                        val dy = touchY - cy

                        if (dx * dx + dy * dy <= radius * radius) {
                            if (selectedSeats.contains(seat.seat_id)) {
                                selectedSeats.remove(seat.seat_id)
                            } else {
                                selectedSeats.add(seat.seat_id)
                            }
                            onSeatSelectionChanged?.invoke(selectedSeats)
                            invalidate()
                            return true
                        }
                    }
                }
            }
        }
        return true
    }

    private fun constrainOffsets() {
        if (scaleFactor <= 1f) {
            offsetX = offsetX.coerceIn(0f, 0f)
            offsetY = offsetY.coerceIn(0f, 0f)
            return
        }

        val scaledWidth = mapWidth * (width / mapWidth.toFloat()) * scaleFactor
        val scaledHeight = mapHeight * (height / mapHeight.toFloat()) * scaleFactor

        val minX = width - scaledWidth
        val minY = height - scaledHeight

        offsetX = offsetX.coerceIn(minX, 0f)
        offsetY = offsetY.coerceIn(minY, 0f)
    }
}
