package com.booble.reservasi.mitra.data.model.api.booking_cancel

import com.google.gson.annotations.SerializedName

data class NumberBookingCancelResponse(

    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("permintaan_pembatalan")
    val number: Int? = null
)
