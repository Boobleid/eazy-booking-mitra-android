package com.booble.reservasi.mitra.data.model.api.room_facility


import com.google.gson.annotations.SerializedName

data class RoomFacilityResponse(
    @SerializedName("list")
    var roomFacilityData: List<RoomFacilityData>? = null,
    @SerializedName("status")
    var status: Boolean? = null
)