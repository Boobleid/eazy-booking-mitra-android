package com.booble.reservasi.mitra.data.model.api.help


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HelpData(
    @SerializedName("jawaban")
    var jawaban: String? = null,
    @SerializedName("pertanyaan")
    var pertanyaan: String? = null
) : Parcelable