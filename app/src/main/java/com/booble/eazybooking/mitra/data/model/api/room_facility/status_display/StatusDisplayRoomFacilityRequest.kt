package com.booble.eazybooking.mitra.data.model.api.room_facility.status_display


import com.google.gson.annotations.SerializedName

data class StatusDisplayRoomFacilityRequest(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("jenis")
    var jenis: String? = null,
    @SerializedName("status")
    var status: String? = null
)