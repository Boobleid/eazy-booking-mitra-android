package com.booble.reservasi.mitra.data.model.api.service.detail_facility


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailFacilitySessionData(
    @SerializedName("harga")
    var harga: Int? = null,
    @SerializedName("hari")
    var hari: List<String>? = null,
    @SerializedName("jam_akhir")
    var jamAkhir: String? = null,
    @SerializedName("jam_mulai")
    var jamMulai: String? = null,
    @SerializedName("sesi_ke")
    var sesiKe: String? = null,
    @SerializedName("kuota")
    var kuota: String? = null,
) : Parcelable