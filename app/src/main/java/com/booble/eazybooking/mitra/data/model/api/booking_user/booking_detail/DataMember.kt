package com.booble.eazybooking.mitra.data.model.api.booking_user.booking_detail


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataMember(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("first_name")
    var firstName: String? = null,
    @SerializedName("phone")
    var phone: String? = null
) : Parcelable