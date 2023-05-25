package com.booble.reservasi.mitra.data.model.api.booking_cancel

import com.google.gson.annotations.SerializedName

data class BookingCancelListRequest(
	@field:SerializedName("limit")
	val limit: String? = null,

	@field:SerializedName("offset")
	val offset: String? = null,

	@field:SerializedName("id_fasilitas")
	val idFasilitas: String? = null,

	@field:SerializedName("id_property")
	val idProperty: String? = null,

	@field:SerializedName("cari")
	val cari: String? = null,

	@field:SerializedName("status")
	val status: String? = null,
)
