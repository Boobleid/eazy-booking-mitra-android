package com.booble.eazybooking.mitra.data.model.api.add_member


import com.google.gson.annotations.SerializedName

data class DeleteMemberServiceRequest(
    @SerializedName("id")
    var id: String? = null
)