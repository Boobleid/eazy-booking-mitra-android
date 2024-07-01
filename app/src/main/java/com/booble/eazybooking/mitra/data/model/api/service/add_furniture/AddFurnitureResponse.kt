package com.booble.eazybooking.mitra.data.model.api.service.add_furniture


import com.google.gson.annotations.SerializedName

data class AddFurnitureResponse(
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
)