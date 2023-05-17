package com.booble.reservasi.mitra.data.model.api.room_facility


import com.google.gson.annotations.SerializedName

data class RoomFacilityRequest(
    @SerializedName("id_layanan")
    var idLayanan: String? = null,
    @SerializedName("limit")
    var limit: String? = null,
    @SerializedName("offset")
    var offset: String? = null
)