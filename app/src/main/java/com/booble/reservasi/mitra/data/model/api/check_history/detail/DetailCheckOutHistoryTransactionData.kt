package com.booble.reservasi.mitra.data.model.api.check_history.detail


import com.google.gson.annotations.SerializedName

data class DetailCheckOutHistoryTransactionData(
    @SerializedName("jenis_tr")
    var jenisTr: String? = null,
    @SerializedName("paket")
    var paket: String? = null
)