package com.ticket.booking.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ticket.booking.R
import com.ticket.booking.data.remote.SeatsType

class SeatTypeAdapter(private val items: List<SeatsType>) :
    RecyclerView.Adapter<SeatTypeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.seatIcon)
        val price: TextView = view.findViewById(R.id.seatPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_seat_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        val iconRes = when (item.seat_type) {
            holder.itemView.context.getString(R.string.vip) -> R.drawable.seat_1
            holder.itemView.context.getString(R.string.comfort) -> R.drawable.seat_2
            holder.itemView.context.getString(R.string.standard) -> R.drawable.seat_3
            else -> R.drawable.seat_booked
        }
        holder.icon.setImageResource(iconRes)

        val price = if (item.price > 0) item.price.toString() else "Занято"
        holder.price.text = price
    }

    override fun getItemCount() = items.size
}