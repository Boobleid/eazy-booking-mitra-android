package com.booble.reservasi.mitra.data.model.api.calendar


import com.google.gson.annotations.SerializedName

data class BookingDateCalendarResponse(
    @SerializedName("list")
    var `data`: List<BookingDateItem>? = null,
    @SerializedName("status")
    var status: Boolean? = null,
    @SerializedName("bulan_lalu")
    var lastMonth: Int? = null,
    @SerializedName("bulan_depan")
    var nextMonth: Int? = null,
)

data class BookingDateItem(
    @SerializedName("day")
    var day: Int? = null,
)