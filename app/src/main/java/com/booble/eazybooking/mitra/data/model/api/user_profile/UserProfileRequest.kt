package com.booble.eazybooking.mitra.data.model.api.user_profile


import com.google.gson.annotations.SerializedName

data class UserProfileRequest(
    @SerializedName("kode_user")
    var kodeUser: String? = null
)