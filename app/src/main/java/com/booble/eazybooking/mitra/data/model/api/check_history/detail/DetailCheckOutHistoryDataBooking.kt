package com.booble.eazybooking.mitra.data.model.api.check_history.detail


import com.google.gson.annotations.SerializedName

data class DetailCheckOutHistoryDataBooking(
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
    var status: Int? = null,
    @SerializedName("tgl_checkin")
    var tglCheckin: String? = null,
    @SerializedName("tgl_checkout")
    var tglCheckout: String? = null,
    @SerializedName("tipe_booking")
    var tipeBooking: String? = null,
    @SerializedName("u_org_lain")
    var uOrgLain: String? = null
)