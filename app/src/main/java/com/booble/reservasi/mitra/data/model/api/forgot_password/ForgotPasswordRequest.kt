package com.booble.reservasi.mitra.data.model.api.forgot_password


import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("email")
    var email: String? = null
)