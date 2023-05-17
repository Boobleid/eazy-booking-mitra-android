package com.booble.reservasi.mitra.data.model.api.firebase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by rivaldy on 27/08/21
 * Find me on my Github -> https://github.com/im-o
 **/

@Parcelize
data class ChatModel(
    val id_message: String? = null,
    val sender: String? = null,
    val receiver: String? = null,
    val message: String? = null,
    val timestamp: String? = null,
    val are_is_seen: Boolean = false
) : Parcelable