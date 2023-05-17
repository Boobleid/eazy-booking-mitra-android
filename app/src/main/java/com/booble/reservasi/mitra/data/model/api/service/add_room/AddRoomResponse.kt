package com.booble.reservasi.mitra.data.model.api.service.add_room


import com.google.gson.annotations.SerializedName

data class AddRoomResponse(
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
)