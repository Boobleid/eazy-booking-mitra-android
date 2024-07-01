package com.booble.eazybooking.mitra.data.model.api.booking_user.item_condition


import com.google.gson.annotations.SerializedName

data class ItemConditionResponse(
    @SerializedName("data")
    var `data`: String? = null,
    @SerializedName("message")
    var message: String? = null
)