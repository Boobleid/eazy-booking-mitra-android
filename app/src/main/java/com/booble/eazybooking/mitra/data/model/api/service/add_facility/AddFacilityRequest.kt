package com.booble.eazybooking.mitra.data.model.api.service.add_facility


import com.google.gson.annotations.SerializedName

data class AddFacilityRequest(
    @SerializedName("action")
    var action: String? = null,
    @SerializedName("deskripsi")
    var deskripsi: String? = null,
    @SerializedName("foto")
    var foto: List<String>? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("id_layanan")
    var idLayanan: String? = null,
    @SerializedName("max_tamu")
    var maxTamu: String? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("harga_tahun")
    var hargaTahun: String? = null,
    @SerializedName("harga_bulan")
    var hargaBulan: String? = null,
    @SerializedName("sesi")
    var addFacilitySessionData: List<AddFacilitySessionData>? = null
)