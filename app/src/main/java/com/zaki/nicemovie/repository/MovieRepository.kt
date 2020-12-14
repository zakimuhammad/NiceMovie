package com.zaki.nicemovie.repository

import com.zaki.nicemovie.api.MovieApi.MovieInstance
import com.zaki.nicemovie.db.MovieDatabase
import com.zaki.nicemovie.model.Movie

class MovieRepository(
    val db: MovieDatabase
) {
    suspend fun getPopularMovies(api: String, pageNumber: Int) =
        MovieInstance.api.getPopularMovie(api, pageNumber)

    suspend fun searchMovie(api: String, searchQuery: String, pageNumber: Int) =
        MovieInstance.api.findMovie(api, searchQuery, pageNumber)

    suspend fun upsertMovie(movie: Movie) = db.getMovieDao().upsert(movie)

    fun getSavedMovie() = db.getMovieDao().getAllMovie()

    suspend fun deleteMovie(movie: Movie) = db.getMovieDao().deleteMovie(movie)
}