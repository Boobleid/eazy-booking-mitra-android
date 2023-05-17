package com.booble.reservasi.mitra.data.model.api.booking_user


import com.google.gson.annotations.SerializedName

data class BookingUserRequest(
    @SerializedName("limit")
    var limit: String? = null,
    @SerializedName("offset")
    var offset: String? = null,
    @SerializedName("search_inv")
    var searchInv: String? = null,
    @SerializedName("tgl_booking")
    var tglBooking: String? = null
)