package com.booble.reservasi.mitra.data.model.api


import com.google.gson.annotations.SerializedName

data class DefaultLimitOffsetRequest(
    @SerializedName("limit")
    var limit: String? = null,
    @SerializedName("offset")
    var offset: String? = null
)