package com.booble.eazybooking.mitra.data.model.api.check_history.detail


import com.google.gson.annotations.SerializedName

data class DetailCheckOutHistoryOtherData(
    @SerializedName("ktp")
    var ktp: String? = null,
    @SerializedName("nm_org_lain")
    var nmOrgLain: String? = null
)