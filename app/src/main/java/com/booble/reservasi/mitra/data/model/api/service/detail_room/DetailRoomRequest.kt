package com.booble.reservasi.mitra.data.model.api.service.detail_room


import com.google.gson.annotations.SerializedName

data class DetailRoomRequest(
    @SerializedName("id")
    var id: String? = null
)