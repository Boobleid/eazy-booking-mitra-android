package com.booble.eazybooking.mitra.data.model.api.room_facility


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoomFacilityData(
    @SerializedName("foto")
    var foto: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("kategori")
    var kategori: String? = null,
    @SerializedName("ket")
    var ket: String? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("display")
    var display: Int? = null,
) : Parcelable