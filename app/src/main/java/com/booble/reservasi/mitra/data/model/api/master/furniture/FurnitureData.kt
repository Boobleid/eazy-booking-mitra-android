package com.booble.reservasi.mitra.data.model.api.master.furniture


import com.google.gson.annotations.SerializedName

data class FurnitureData(
    @SerializedName("foto")
    var foto: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("nilai")
    var nilai: String? = null
)