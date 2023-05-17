package com.booble.reservasi.mitra.data.model.api.booking_user.item_condition


import com.google.gson.annotations.SerializedName

data class ItemConditionRequest(
    @SerializedName("bukti")
    var bukti: String? = null,
    @SerializedName("keterangan_barang")
    var keteranganBarang: String? = null,
    @SerializedName("kode_barang")
    var kodeBarang: String? = null,
    @SerializedName("kode_booking")
    var kodeBooking: String? = null,
    @SerializedName("kode_pembelian")
    var kodePembelian: String? = null,
    @SerializedName("status_barang")
    var statusBarang: String? = null
)