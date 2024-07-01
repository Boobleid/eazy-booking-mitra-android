package com.booble.eazybooking.mitra.data.model.api.service


import com.google.gson.annotations.SerializedName

data class ServiceRequest(
    @SerializedName("cari")
    var cari: String? = null
)