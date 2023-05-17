package com.booble.reservasi.mitra.data.model.api.master.facility_room


import com.google.gson.annotations.SerializedName

data class FacilityRoomResponse(
    @SerializedName("list")
    var facilityRoomData: List<FacilityRoomData>? = null,
    @SerializedName("status")
    var status: Boolean? = null
)