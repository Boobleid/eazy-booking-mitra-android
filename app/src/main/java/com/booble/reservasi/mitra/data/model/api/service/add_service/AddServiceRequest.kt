package com.booble.reservasi.mitra.data.model.api.service.add_service


import com.google.gson.annotations.SerializedName

data class AddServiceRequest(
    @SerializedName("action")
    var action: String? = null,
    @SerializedName("alamat")
    var alamat: String? = null,
    @SerializedName("foto")
    var foto: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("id_kategori")
    var idKategori: String? = null,
    @SerializedName("id_kota")
    var idKota: String? = null,
    @SerializedName("lat")
    var lat: String? = null,
    @SerializedName("lng")
    var lng: String? = null,
    @SerializedName("nama")
    var nama: String? = null
)