package com.booble.eazybooking.mitra.data.model.api.master.city


import com.google.gson.annotations.SerializedName

data class CityData(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("nama")
    var nama: String? = null
)