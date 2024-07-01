package com.booble.eazybooking.mitra.data.model.api.movie


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
        var movieResults: List<com.booble.eazybooking.mitra.data.model.api.movie.MovieResult>? = null,
    @SerializedName("page")
        var page: Int? = null,
    @SerializedName("total_pages")
        var totalPages: Int? = null,
    @SerializedName("total_results")
        var totalResults: Int? = null
)