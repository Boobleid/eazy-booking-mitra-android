package com.booble.eazybooking.mitra.data.model.api.master.furniture


import com.google.gson.annotations.SerializedName

data class FurnitureResponse(
    @SerializedName("list")
    var furnitureData: List<FurnitureData>? = null,
    @SerializedName("status")
    var status: Boolean? = null
)