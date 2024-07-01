package com.booble.eazybooking.mitra.data.model.api.check_history


import com.google.gson.annotations.SerializedName

data class CheckOutHistoryResponse(
    @SerializedName("list")
    var `data`: List<CheckOutHistoryData>? = null,
    @SerializedName("message")
    var message: String? = null
)