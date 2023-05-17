package com.booble.reservasi.mitra.data.model.api.verfication_check_in


import com.google.gson.annotations.SerializedName

data class VerificationCheckInRequest(
    @SerializedName("kode_booking")
    var kodeBooking: String? = null,
    @SerializedName("kode_pembelian")
    var kodePembelian: String? = null
)