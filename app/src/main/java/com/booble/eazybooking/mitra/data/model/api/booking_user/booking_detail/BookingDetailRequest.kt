package com.booble.eazybooking.mitra.data.model.api.booking_user.booking_detail


import com.google.gson.annotations.SerializedName

data class BookingDetailRequest(
    @SerializedName("kode_booking")
    var kodeBooking: String? = null
)