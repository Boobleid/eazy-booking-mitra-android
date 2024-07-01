package com.booble.eazybooking.mitra.data.model.api.check_history.detail


import com.google.gson.annotations.SerializedName

data class DetailCheckOutHistoryResponse(
    @SerializedName("data")
    var `data`: DetailCheckOutHistoryData? = null,
    @SerializedName("data_booking")
    var dataBooking: DetailCheckOutHistoryDataBooking? = null,
    @SerializedName("data_member")
    var dataMember: DetailCheckOutHistoryDataMember? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("org_lain")
    var dataOther: DetailCheckOutHistoryOtherData? = null,
    @SerializedName("transaksi")
    var dataTransaction: DetailCheckOutHistoryTransactionData? = null
)