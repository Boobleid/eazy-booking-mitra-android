package com.booble.reservasi.mitra.data.model.api.service.add_service


import com.google.gson.annotations.SerializedName

data class AddServiceResponse(
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
)