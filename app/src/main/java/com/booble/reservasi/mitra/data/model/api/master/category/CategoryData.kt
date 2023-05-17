package com.booble.reservasi.mitra.data.model.api.master.category


import com.google.gson.annotations.SerializedName

data class CategoryData(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("nama")
    var nama: String? = null
)