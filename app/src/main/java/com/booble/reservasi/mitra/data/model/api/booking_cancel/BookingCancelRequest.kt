package com.booble.reservasi.mitra.data.model.api.booking_cancel

import com.google.gson.annotations.SerializedName

data class BookingCancelRequest(

	@field:SerializedName("invoice")
	val invoice: String? = null,

	@field:SerializedName("waktu_lokal")
	val waktuLokal: String? = null,

	@field:SerializedName("alasan_tolak")
	val alasanTolak: String? = null,

	@field:SerializedName("status")
	val status: String? = null,
)
