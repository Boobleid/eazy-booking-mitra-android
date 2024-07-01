package com.booble.eazybooking.mitra.data.model.api.booking_user


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingUserData(
    @SerializedName("create_date")
    var createDate: String? = null,
    @SerializedName("first_name")
    var firstName: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("id_member")
    var idMember: Int? = null,
    @SerializedName("id_transaksi")
    var idTransaksi: String? = null,
    @SerializedName("kode_booking")
    var kodeBooking: String? = null,
    @SerializedName("kode_pembelian")
    var kodePembelian: String? = null,
    @SerializedName("nm_produk")
    var nmProduk: String? = null,
    @SerializedName("nm_property")
    var nmProperty: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("tgl_checkin")
    var tglCheckin: String? = null,
    @SerializedName("tgl_checkout")
    var tglCheckout: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("phone")
    var phone: String? = null
) : Parcelable