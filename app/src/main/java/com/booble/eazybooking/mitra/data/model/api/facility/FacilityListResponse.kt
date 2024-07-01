package com.booble.eazybooking.mitra.data.model.api.facility


import com.google.gson.annotations.SerializedName

data class FacilityListResponse(
    @SerializedName("list")
    var `data`: List<FacilityItem>? = null,
    @SerializedName("status")
    var status: Boolean? = null
)

data class FacilityItem(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("nm_produk")
    var productName: String? = null,
) {
    override fun toString(): String {
        return productName ?: ""
    }
}