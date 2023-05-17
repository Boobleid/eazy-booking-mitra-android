package com.booble.reservasi.mitra.data.model.api.master.facility_building


import com.google.gson.annotations.SerializedName

data class FacilityBuildingData(
    @SerializedName("foto")
    var foto: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("nama")
    var nama: String? = null
)