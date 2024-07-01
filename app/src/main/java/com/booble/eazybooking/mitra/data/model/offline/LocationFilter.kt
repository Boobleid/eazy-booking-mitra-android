package com.booble.eazybooking.mitra.data.model.offline

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationFilter(
    var date: String? = null,
    var locationId: String? = null,
    var locationName: String? = null,
    var facilityId: String? = null,
    var facilityName: String? = null,
) : Parcelable