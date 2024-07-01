package com.booble.eazybooking.mitra.data.model.api.booking_user.booking_detail


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataBooking(
    @SerializedName("create_date")
    var createDate: String? = null,
    @SerializedName("gambar_ktp")
    var gambarKtp: String? = null,
    @SerializedName("id_member")
    var idMember: Int? = null,
    @SerializedName("id_transaksi")
    var idTransaksi: String? = null,
    @SerializedName("kode_booking")
    var kodeBooking: String? = null,
    @SerializedName("kode_card")
    var kodeCard: String? = null,
    @SerializedName("kode_pembelian")
    var kodePembelian: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("tgl_checkin")
    var tglCheckin: String? = null,
    @SerializedName("tgl_checkout")
    var tglCheckout: String? = null,
    @SerializedName("tipe_booking")
    var tipeBooking: String? = null,
    @SerializedName("u_org_lain")
    var uOrgLain: String? = null
) :Parcelable