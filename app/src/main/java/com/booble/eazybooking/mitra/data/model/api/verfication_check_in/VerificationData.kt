package com.booble.eazybooking.mitra.data.model.api.verfication_check_in


import com.google.gson.annotations.SerializedName

data class VerificationData(
    @SerializedName("kode_booking")
    var kodeBooking: String? = null,
    @SerializedName("kode_pembelian")
    var kodePembelian: String? = null,
    @SerializedName("last_update")
    var lastUpdate: String? = null,
    @SerializedName("status_booking")
    var statusBooking: String? = null
)