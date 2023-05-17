package com.booble.reservasi.mitra.data.model.api.service.detail_facility


import com.google.gson.annotations.SerializedName

data class DetailFacilityRequest(
    @SerializedName("id")
    var id: String? = null
)