package com.zaki.nicemovie.model

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @field:SerializedName("results")
    val results: MutableList<Movie>,

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val total_pages: Int,

    @field:SerializedName("total_results")
    val total_results: Int,
)