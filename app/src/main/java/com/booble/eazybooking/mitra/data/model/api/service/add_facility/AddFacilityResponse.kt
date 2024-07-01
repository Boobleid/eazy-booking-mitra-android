package com.booble.eazybooking.mitra.data.model.api.service.add_facility


import com.google.gson.annotations.SerializedName

data class AddFacilityResponse(
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
)