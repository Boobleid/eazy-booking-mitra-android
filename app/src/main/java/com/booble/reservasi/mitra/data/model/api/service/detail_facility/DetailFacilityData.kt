package com.booble.reservasi.mitra.data.model.api.service.detail_facility


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailFacilityData(
    @SerializedName("deskripsi")
    var deskripsi: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("jml_tamu")
    var jmlTamu: String? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("sesi")
    var detailFacilitySessionData: List<DetailFacilitySessionData>? = null,
    @SerializedName("images")
    var images: MutableList<String>? = null
) : Parcelable