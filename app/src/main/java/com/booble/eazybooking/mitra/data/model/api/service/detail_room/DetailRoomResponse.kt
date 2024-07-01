package com.booble.eazybooking.mitra.data.model.api.service.detail_room


import com.google.gson.annotations.SerializedName

data class DetailRoomResponse(
    @SerializedName("data")
    var detailRoomData: DetailRoomData? = null,
    @SerializedName("status")
    var status: Boolean? = null
)