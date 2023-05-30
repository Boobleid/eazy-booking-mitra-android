package com.booble.reservasi.mitra.data.model.api.user_profile


import com.google.gson.annotations.SerializedName

data class UserProfileData(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("nama")
    var firstName: String? = null,
    @SerializedName("kode_mitra")
    var idMember: String? = null,
    @SerializedName("nohp")
    var phone: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("saldo")
    var saldo: String? = null,
    @SerializedName("bank")
    var bank: String? = null,
    @SerializedName("rekening")
    var rekening: String? = null,
    @SerializedName("pemilikik_rek")
    var pemilikRekening: String? = null
)