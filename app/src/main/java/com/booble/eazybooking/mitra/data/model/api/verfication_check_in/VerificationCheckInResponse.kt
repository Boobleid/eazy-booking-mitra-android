package com.booble.eazybooking.mitra.data.model.api.verfication_check_in


import com.google.gson.annotations.SerializedName

data class VerificationCheckInResponse(
    @SerializedName("data_booking_1")
    var dataBooking1: VerificationData? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
)