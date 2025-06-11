package com.ticket.booking.data

import com.ticket.booking.data.remote.Seat as AppSeat
import com.ticket.hallmapview.Seat as UiSeat

fun AppSeat.toUiSeat(): UiSeat {
    return UiSeat(
        booked_seats = this.booked_seats,
        left = this.left,
        object_description = this.object_description,
        object_title = this.object_title,
        object_type = this.object_type,
        place = this.place,
        place_name = this.place_name ?: "",
        row_num = this.row_num,
        seat_id = this.seat_id,
        seat_type = this.seat_type ?: "",
        seat_view = this.seat_view,
        sector = this.sector ?: "",
        top = this.top
    )
}