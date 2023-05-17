package com.booble.reservasi.mitra.data.model.api.master.city


import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("list")
    var cityData: List<CityData>? = null,
    @SerializedName("status")
    var status: Boolean? = null
)