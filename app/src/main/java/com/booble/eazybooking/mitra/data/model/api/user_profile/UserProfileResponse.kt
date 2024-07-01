package com.booble.eazybooking.mitra.data.model.api.user_profile


import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("data")
    var `data`: UserProfileData? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
)