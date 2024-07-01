package com.booble.eazybooking.mitra.data.model.api.booking_user.booking_detail


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataOther(
    @SerializedName("ktp")
    var ktp: String? = null,
    @SerializedName("nm_org_lain")
    var nmOrgLain: String? = null
) : Parcelable