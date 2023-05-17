package com.booble.reservasi.mitra.data.model.api.service.add_room


import com.google.gson.annotations.SerializedName

data class AddRoomRequest(
    @SerializedName("action")
    var action: String? = null,
    @SerializedName("brg_dlm_ruangan")
    var brgDlmRuangan: List<String>? = null,
    @SerializedName("deskripsi")
    var deskripsi: String? = null,
    @SerializedName("fasilitas_gedung")
    var fasilitasGedung: List<String>? = null,
    @SerializedName("fasilitas_ruangan")
    var fasilitasRuangan: List<String>? = null,
    @SerializedName("foto")
    var foto: List<String>? = null,
    @SerializedName("harga_beli")
    var hargaBeli: String? = null,
    @SerializedName("harga_hari")
    var hargaHari: String? = null,
    @SerializedName("harga_bulan")
    var hargaBulan: String? = null,
    @SerializedName("harga_tahun")
    var hargaTahun: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("id_layanan")
    var idLayanan: String? = null,
    @SerializedName("jml_kamar_mandi")
    var jmlKamarMandi: String? = null,
    @SerializedName("jml_tamu")
    var jmlTamu: String? = null,
    @SerializedName("jml_tmpt_tidur")
    var jmlTmptTidur: String? = null,
    @SerializedName("luas_kamar")
    var luasKamar: String? = null,
    @SerializedName("maks_booking")
    var maksBooking: String? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("no_kamar")
    var roomNumbers: List<String>? = null,
)