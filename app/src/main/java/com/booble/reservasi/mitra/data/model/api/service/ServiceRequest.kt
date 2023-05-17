package com.booble.reservasi.mitra.data.model.api.service


import com.google.gson.annotations.SerializedName

data class ServiceRequest(
    @SerializedName("cari")
    var cari: String? = null
)