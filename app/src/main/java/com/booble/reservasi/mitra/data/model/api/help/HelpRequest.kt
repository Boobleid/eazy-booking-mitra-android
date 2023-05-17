package com.booble.reservasi.mitra.data.model.api.help


import com.google.gson.annotations.SerializedName

data class HelpRequest(
    @SerializedName("app")
    var app: String? = null,
    @SerializedName("id_topik")
    var idTopik: String? = null
)