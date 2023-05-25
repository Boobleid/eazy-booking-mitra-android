package com.booble.reservasi.mitra.data.model.api.booking_cancel

import com.google.gson.annotations.SerializedName

data class SendMessageConversationRequest(

	@field:SerializedName("invoice")
	val invoice: String? = null,

	@field:SerializedName("waktu_lokal")
	val waktuLokal: String? = null,

	@field:SerializedName("pesan")
	val pesan: String? = null
)
