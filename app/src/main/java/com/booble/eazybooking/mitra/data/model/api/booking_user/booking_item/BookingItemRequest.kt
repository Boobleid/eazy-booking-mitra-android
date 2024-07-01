package com.booble.eazybooking.mitra.data.model.api.booking_user.booking_item


import com.google.gson.annotations.SerializedName

data class BookingItemRequest(
    @SerializedName("kode_booking")
    var kodeBooking: String? = null,
    @SerializedName("kode_pembelian")
    var kodePembelian: String? = null
)