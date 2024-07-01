package com.booble.eazybooking.mitra.data.model.api.facility


import com.google.gson.annotations.SerializedName

data class FacilityListRequest(
    @SerializedName("id_property")
    var propertyId: String? = null
)