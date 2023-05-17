package com.booble.reservasi.mitra.data.model.api.withdraw


import com.google.gson.annotations.SerializedName

data class WithdrawRequest(
    @SerializedName("bank")
    var bank: String? = null,
    @SerializedName("jumlah")
    var jumlah: String? = null,
    @SerializedName("norek")
    var norek: String? = null,
    @SerializedName("pemilik")
    var pemilik: String? = null
)