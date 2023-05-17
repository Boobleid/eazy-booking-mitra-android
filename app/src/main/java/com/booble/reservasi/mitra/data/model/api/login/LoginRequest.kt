package com.booble.reservasi.mitra.data.model.api.login


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("id_hp")
    var userUID: String? = null
)