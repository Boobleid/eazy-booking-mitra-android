package com.booble.reservasi.mitra.data.model.api.add_member


import com.google.gson.annotations.SerializedName

data class AddMemberServiceRequest(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("id_layanan")
    var idLayanan: String? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("nohp")
    var nohp: String? = null,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("action")
    var action: String? = null,
    @SerializedName("id_member")
    var idMember: String? = null
)