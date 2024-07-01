package com.booble.eazybooking.mitra.data.model.api.login


import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("id_user")
    var idUser: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("nohp")
    var phone: String? = null,
    @SerializedName("nama")
    var name: String? = null,
    @SerializedName("token")
    var token: String? = null
)