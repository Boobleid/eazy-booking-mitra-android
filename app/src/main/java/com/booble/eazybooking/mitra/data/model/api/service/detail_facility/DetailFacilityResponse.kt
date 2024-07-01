package com.booble.eazybooking.mitra.data.model.api.service.detail_facility


import com.google.gson.annotations.SerializedName

data class DetailFacilityResponse(
    @SerializedName("data")
    var detailFacilityData: DetailFacilityData? = null,
    @SerializedName("status")
    var status: Boolean? = null
)