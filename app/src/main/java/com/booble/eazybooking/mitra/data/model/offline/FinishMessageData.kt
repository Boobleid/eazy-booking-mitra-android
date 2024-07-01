package com.booble.eazybooking.mitra.data.model.offline

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by rivaldy on 11/08/21
 * Find me on my Github -> https://github.com/im-o
 **/

@Parcelize
data class FinishMessageData(
    val status: Boolean? = false,
    val message: String? = "",
    val type: String? = "",
    val more: String? = "",
    val head: String? = ""
) : Parcelable