package com.booble.eazybooking.mitra.data.model.api.service.add_furniture


import com.google.gson.annotations.SerializedName

data class AddFurnitureRequest(
    @SerializedName("action")
    var action: String? = null,
    @SerializedName("foto")
    var foto: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("nilai_barang")
    var nilaiBarang: String? = null
)