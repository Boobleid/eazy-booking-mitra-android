package com.booble.reservasi.mitra.data.model.api.check_history.detail


import com.google.gson.annotations.SerializedName

data class DetailCheckOutHistoryRequest(
    @SerializedName("kode_booking")
    var kodeBooking: String? = null
)