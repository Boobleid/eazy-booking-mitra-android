package com.booble.reservasi.mitra.data.model.api.booking_user.booking_item


import com.google.gson.annotations.SerializedName

data class BookingItemResponse(
    @SerializedName("barang")
    var bookingItemData: List<BookingItemData>? = null,
    @SerializedName("total_barang_rusak")
    var totalDamagedGoods: TotalDamageData? = null,
    @SerializedName("message")
    var message: String? = null
)