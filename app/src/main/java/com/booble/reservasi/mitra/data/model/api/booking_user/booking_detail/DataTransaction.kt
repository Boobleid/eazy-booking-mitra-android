package com.booble.reservasi.mitra.data.model.api.booking_user.booking_detail


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataTransaction(
    @SerializedName("jenis_tr")
    var jenisTr: String? = null,
    @SerializedName("paket")
    var paket: String? = null
) : Parcelable