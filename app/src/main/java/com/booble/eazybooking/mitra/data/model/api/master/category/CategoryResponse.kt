package com.booble.eazybooking.mitra.data.model.api.master.category


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("list")
    var categoryData: List<CategoryData>? = null,
    @SerializedName("status")
    var status: Boolean? = null
)