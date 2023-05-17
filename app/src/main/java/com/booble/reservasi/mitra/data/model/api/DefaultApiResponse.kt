package com.booble.reservasi.mitra.data.model.api


import com.google.gson.annotations.SerializedName

data class DefaultApiResponse(
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
)