package com.zaki.nicemovie.api

import com.zaki.nicemovie.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("discover/movie")
    fun getPopularMovie(
        @Query("api_key") api: String,
        @Query("page") pageNumber: Int = 1
    ): Response<MovieResponse>


    @GET("search/movie")
    fun findMovie(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("page") pageNumber: Int = 1
    ): Response<MovieResponse>
}