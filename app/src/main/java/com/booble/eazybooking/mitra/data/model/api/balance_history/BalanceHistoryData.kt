package com.booble.eazybooking.mitra.data.model.api.balance_history


import com.google.gson.annotations.SerializedName

data class BalanceHistoryData(
    @SerializedName("invoice")
    var invoice: String? = null,
    @SerializedName("jumlah_bonus")
    var jumlahBonus: String? = null,
    @SerializedName("keterangan")
    var keterangan: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("tanggal")
    var tanggal: String? = null
)