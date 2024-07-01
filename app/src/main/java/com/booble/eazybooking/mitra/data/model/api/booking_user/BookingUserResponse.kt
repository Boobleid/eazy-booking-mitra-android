package com.booble.eazybooking.mitra.data.model.api.booking_user


import com.google.gson.annotations.SerializedName

data class BookingUserResponse(
    @SerializedName("data")
    var `data`: List<BookingUserData>? = null,
    @SerializedName("message")
    var message: String? = null
)