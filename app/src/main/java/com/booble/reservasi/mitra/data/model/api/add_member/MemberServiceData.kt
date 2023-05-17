package com.booble.reservasi.mitra.data.model.api.add_member


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberServiceData(
    @SerializedName("active")
    var active: Int? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("id_property")
    var idProperty: Int? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("nm_property")
    var nmProperty: String? = null,
    @SerializedName("pass_text")
    var passText: String? = null,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("username")
    var username: String? = null
) : Parcelable