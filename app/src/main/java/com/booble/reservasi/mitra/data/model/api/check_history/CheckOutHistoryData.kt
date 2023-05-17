package com.booble.reservasi.mitra.data.model.api.check_history


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckOutHistoryData(
    @SerializedName("bayar")
    var bayar: Int? = null,
    @SerializedName("bayar_millennial")
    var bayarMillennial: String? = null,
    @SerializedName("bayar_mitra")
    var bayarMitra: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("invoice")
    var invoice: String? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("nm_produk")
    var nmProduk: String? = null,
    @SerializedName("nm_property")
    var nmProperty: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("telp")
    var telp: String? = null,
    @SerializedName("tgl")
    var tgl: String? = null,
    @SerializedName("tgl_checkin")
    var tglCheckin: String? = null,
    @SerializedName("tgl_checkout")
    var tglCheckout: String? = null,
    @SerializedName("kode_member")
    var memberCode: String? = null
) : Parcelable