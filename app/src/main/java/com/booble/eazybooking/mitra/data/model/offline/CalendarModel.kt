package com.booble.eazybooking.mitra.data.model.offline

import java.util.*

data class CalendarModel(
    var date: Int,
    var month: Int,
    var year : Int,
    var calendarCompare : Calendar,
    var isBooking : Boolean?
) {
    override fun toString(): String {
        return "Date = $date, Month = $month, Year = $year, IsBooking = $isBooking"
    }
}