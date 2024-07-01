package com.booble.eazybooking.mitra.data.model.api.help.contact


import com.google.gson.annotations.SerializedName

data class ContactResponse(
    @SerializedName("data")
    var contactData: List<ContactData>? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
)