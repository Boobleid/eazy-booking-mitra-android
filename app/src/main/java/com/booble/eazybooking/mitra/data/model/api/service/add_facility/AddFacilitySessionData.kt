package com.booble.eazybooking.mitra.data.model.api.service.add_facility


import com.google.gson.annotations.SerializedName

data class AddFacilitySessionData(
    @SerializedName("harga")
    var harga: String? = null,
    @SerializedName("hari")
    var hari: List<String>? = null,
    @SerializedName("jam_akhir")
    var jamAkhir: String? = null,
    @SerializedName("jam_mulai")
    var jamMulai: String? = null,
    @SerializedName("sesi_ke")
    var sesiKe: String? = null,
    @SerializedName("kuota")
    var kuota: String? = null,
    @SerializedName("time_stamp")
    var timeStamp: Long? = 0
)