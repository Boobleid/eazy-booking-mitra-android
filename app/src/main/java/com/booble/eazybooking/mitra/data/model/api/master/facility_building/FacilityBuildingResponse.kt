package com.booble.eazybooking.mitra.data.model.api.master.facility_building


import com.google.gson.annotations.SerializedName

data class FacilityBuildingResponse(
    @SerializedName("list")
    var facilityBuildingData: List<FacilityBuildingData>? = null,
    @SerializedName("status")
    var status: Boolean? = null
)