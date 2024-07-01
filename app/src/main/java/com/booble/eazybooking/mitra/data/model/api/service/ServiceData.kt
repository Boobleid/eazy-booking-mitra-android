package com.booble.eazybooking.mitra.data.model.api.service


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceData(
    @SerializedName("alamat")
    var alamat: String? = null,
    @SerializedName("foto")
    var foto: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("id_kategori")
    var idKategori: Int? = null,
    @SerializedName("id_kota")
    var idKota: Int? = null,
    @SerializedName("lat")
    var lat: String? = null,
    @SerializedName("lng")
    var lng: String? = null,
    @SerializedName("nama")
    var nama: String? = null
) : Parcelable {
    override fun toString(): String {
        return nama ?: ""
    }
}