package com.booble.eazybooking.mitra.data.model.api


import com.google.gson.annotations.SerializedName

data class DefaultLimitOffsetRequest(
    @SerializedName("limit")
    var limit: String? = null,
    @SerializedName("offset")
    var offset: String? = null,
    @SerializedName("cari")
    var cari: String? = null,
    @SerializedName("tgl")
    var tanggal: String? = null,
    @SerializedName("id_property")
    var propertyId: String? = null,
    @SerializedName("id_fasilitas")
    var fasilitasId: String? = null
)