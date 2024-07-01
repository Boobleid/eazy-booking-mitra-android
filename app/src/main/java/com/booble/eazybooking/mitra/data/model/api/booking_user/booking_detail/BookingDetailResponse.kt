package com.booble.eazybooking.mitra.data.model.api.booking_user.booking_detail


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingDetailResponse(
    @SerializedName("data")
    var `data`: BookingDetailData? = null,
    @SerializedName("data_booking")
    var dataBooking: DataBooking? = null,
    @SerializedName("data_member")
    var dataMember: DataMember? = null,
    @SerializedName("transaksi")
    var dataTransaction: DataTransaction? = null,
    @SerializedName("org_lain")
    var dataOther: DataOther? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
) : Parcelable