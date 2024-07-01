package com.booble.eazybooking.mitra.data.model.api.check_history.detail


import com.google.gson.annotations.SerializedName

data class DetailCheckOutHistoryDataMember(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("first_name")
    var firstName: String? = null,
    @SerializedName("phone")
    var phone: String? = null
)