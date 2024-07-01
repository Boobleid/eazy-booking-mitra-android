package com.booble.eazybooking.mitra.data.model.api.booking_user.booking_detail


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingDetailData(
    @SerializedName("banner")
    var banner: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("nm_layanan")
    var nmLayanan: String? = null,
    @SerializedName("nm_produk")
    var nmProduk: String? = null,
    @SerializedName("nm_property")
    var nmProperty: String? = null,
) : Parcelable