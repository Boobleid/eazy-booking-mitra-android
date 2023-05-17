package com.booble.reservasi.mitra.data.model.api.add_member


import com.google.gson.annotations.SerializedName

data class MemberServiceResponse(
    @SerializedName("list")
    var `data`: List<MemberServiceData>? = null,
    @SerializedName("status")
    var status: Boolean? = null
)