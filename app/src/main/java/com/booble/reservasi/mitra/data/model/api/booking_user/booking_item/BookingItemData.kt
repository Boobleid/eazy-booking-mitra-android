package com.booble.reservasi.mitra.data.model.api.booking_user.booking_item


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingItemData(
    @SerializedName("banner")
    var banner: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("nilai_barang")
    var nilaiBarang: String? = null,
    @SerializedName("nm_barang")
    var nmBarang: String? = null,
    @SerializedName("statusbarang")
    var status: String? = null
) : Parcelable