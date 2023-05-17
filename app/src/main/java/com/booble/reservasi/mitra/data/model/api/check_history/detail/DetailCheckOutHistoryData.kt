package com.booble.reservasi.mitra.data.model.api.check_history.detail


import com.google.gson.annotations.SerializedName

data class DetailCheckOutHistoryData(
    @SerializedName("banner")
    var banner: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("nm_layanan")
    var nmLayanan: String? = null,
    @SerializedName("nm_produk")
    var nmProduk: String? = null,
    @SerializedName("nm_property")
    var nmProperty: String? = null
)