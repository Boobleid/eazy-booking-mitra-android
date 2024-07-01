package com.booble.eazybooking.mitra.data.model.api.user_profile.update_profile


import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("nama")
    var firstName: String? = null,
    @SerializedName("nohp")
    var phone: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("passbaru")
    var newPassword: String? = null,
    @SerializedName("passlama")
    var oldPassword: String? = null
)