package com.booble.reservasi.mitra.data.model.api.service.detail_room


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailRoomData(
    @SerializedName("brg_dlm_ruangan")
    var brgDlmRuangan: MutableList<String>? = mutableListOf(),
    @SerializedName("deskripsi")
    var deskripsi: String? = null,
    @SerializedName("fasilitas_gedung")
    var fasilitasGedung: MutableList<String>? = mutableListOf(),
    @SerializedName("fasilitas_ruangan")
    var fasilitasRuangan: MutableList<String>? = mutableListOf(),
    @SerializedName("images")
    var images: MutableList<String>? = mutableListOf(),
    @SerializedName("harga_bulan")
    var hargaBulan: String? = null,
    @SerializedName("harga_hari")
    var hargaHari: String? = null,
    @SerializedName("harga_jual")
    var hargaJual: String? = null,
    @SerializedName("harga_tahun")
    var hargaTahun: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("jml_k_mandi")
    var jmlKMandi: String? = null,
    @SerializedName("jml_t_tidur")
    var jmlTTidur: String? = null,
    @SerializedName("jml_tamu")
    var jmlTamu: String? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("ukuran_ruangan")
    var ukuranRuangan: String? = null,
    @SerializedName("max_booking")
    var maxBooking: String? = null,
    @SerializedName("no_kamar")
    var roomNumbers: MutableList<String>? = mutableListOf(),
) : Parcelable