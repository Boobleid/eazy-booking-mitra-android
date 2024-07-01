package com.booble.eazybooking.mitra.data.model.api.balance_history


import com.google.gson.annotations.SerializedName

data class BalanceHistoryResponse(
    @SerializedName("bonusKeluar")
    var bonusKeluar: Int? = null,
    @SerializedName("bonusMasuk")
    var bonusMasuk: Int? = null,
    @SerializedName("DataBonus")
    var dataBonus: String? = null,
    @SerializedName("history")
    var balanceHistoryData: List<BalanceHistoryData>? = null,
    @SerializedName("message")
    var message: String? = null
)