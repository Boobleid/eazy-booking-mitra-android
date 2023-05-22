package com.booble.reservasi.mitra.data.model.api.calendar


import com.google.gson.annotations.SerializedName

data class BookingDateCalendarRequest(
    @SerializedName("tahun")
    var tahun: String? = null,
    @SerializedName("bulan")
    var bulan: String? = null,
    @SerializedName("id_property")
    var propertyId: String? = null,
    @SerializedName("id_fasilitas")
    var fasilitasId: String? = null,
)