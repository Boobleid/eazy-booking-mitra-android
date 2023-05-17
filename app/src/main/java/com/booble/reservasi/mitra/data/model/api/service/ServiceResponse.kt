package com.booble.reservasi.mitra.data.model.api.service


import com.google.gson.annotations.SerializedName

data class ServiceResponse(
    @SerializedName("list")
    var data: List<ServiceData>? = null,
    @SerializedName("status")
    var status: Boolean? = null
)