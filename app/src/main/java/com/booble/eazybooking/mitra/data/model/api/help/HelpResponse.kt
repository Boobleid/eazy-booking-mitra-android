package com.booble.eazybooking.mitra.data.model.api.help


import com.google.gson.annotations.SerializedName

data class HelpResponse(
    @SerializedName("data")
    var `data`: List<HelpData>? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
)