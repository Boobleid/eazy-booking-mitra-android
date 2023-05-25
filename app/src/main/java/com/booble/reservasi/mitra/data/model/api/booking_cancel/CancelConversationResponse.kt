package com.booble.reservasi.mitra.data.model.api.booking_cancel

import com.google.gson.annotations.SerializedName

data class CancelConversationResponse(

	@field:SerializedName("data")
	val data: ArrayList<ConversationItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class ConversationItem(

	@field:SerializedName("pesan")
	val pesan: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("belum_dibaca")
	val belumDibaca: Int? = null,

	@field:SerializedName("left_position")
	val leftPosition: Int? = null,

	@field:SerializedName("right_position")
	val rightPosition: Int? = null
)
