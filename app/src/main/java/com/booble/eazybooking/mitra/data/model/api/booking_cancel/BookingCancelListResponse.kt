package com.booble.eazybooking.mitra.data.model.api.booking_cancel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class BookingCancelListResponse(

    @field:SerializedName("list")
    val list: ArrayList<BookingCancelItem>? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

@Parcelize
data class BookingCancelItem(
    @field:SerializedName("bayar_millennial")
    val bayarMillennial: String? = null,

    @field:SerializedName("pesan_belum_dibaca")
    val pesanBelumDibaca: Int? = null,

    @field:SerializedName("telp")
    val telp: String? = null,

    @field:SerializedName("tgl_booking")
    val tglBooking: String? = null,

    @field:SerializedName("kode_member")
    val kodeMember: String? = null,

    @field:SerializedName("nm_property")
    val nmProperty: String? = null,

    @field:SerializedName("bayar_mitra")
    val bayarMitra: String? = null,

    @field:SerializedName("tgl_create_batal")
    val tglCreateBatal: String? = null,

    @field:SerializedName("tgl_checkout")
    val tglCheckout: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("biaya_admin")
    val biayaAdmin: Int? = null,

    @field:SerializedName("tgl_confirm_batal")
    val tglConfirmBatal: String? = null,

    @field:SerializedName("tgl")
    val tgl: String? = null,

    @field:SerializedName("bayar")
    val bayar: Int? = null,

    @field:SerializedName("status_batal")
    val statusBatal: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("invoice")
    val invoice: String? = null,

    @field:SerializedName("nm_produk")
    val nmProduk: String? = null,

    @field:SerializedName("tgl_checkin")
    val tglCheckin: String? = null,

    @field:SerializedName("sesi")
    val sesi: ArrayList<SesiItem>? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("alasan_batal")
    val alasanBatal: String? = null,

    @field:SerializedName("alasan_tolak")
    val alasanTolak: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable

@Parcelize
data class SesiItem(

    @field:SerializedName("harga")
    val harga: Int? = null,

    @field:SerializedName("jam")
    val jam: String? = null,

    @field:SerializedName("sesi_ke")
    val sesiKe: String? = null,

    @field:SerializedName("disc")
    val disc: Int? = null
) : Parcelable
