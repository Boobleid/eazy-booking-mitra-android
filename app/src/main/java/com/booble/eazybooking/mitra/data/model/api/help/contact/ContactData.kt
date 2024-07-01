package com.booble.eazybooking.mitra.data.model.api.help.contact


import com.google.gson.annotations.SerializedName

data class ContactData(
    @SerializedName("gambar")
    var gambar: String? = null,
    @SerializedName("kontak")
    var kontak: String? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("url")
    var url: String? = null
)