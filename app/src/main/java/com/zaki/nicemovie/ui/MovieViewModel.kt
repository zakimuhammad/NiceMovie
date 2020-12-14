package com.zaki.nicemovie.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zaki.nicemovie.model.Movie
import com.zaki.nicemovie.model.MovieResponse
import com.zaki.nicemovie.repository.MovieRepository
import com.zaki.nicemovie.util.Constant.Companion.API_KEY
import com.zaki.nicemovie.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MovieViewModel(
    app: Application,
    private val movieRepository: MovieRepository
) : AndroidViewModel(app){

    val popularMovie: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    var popularMoviePage = 1
    var movieResponse: MovieResponse? = null


    val searchMovie: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    var searchMoviePage = 1
    var searchResponse: MovieResponse? = null

    init {
        getPopularMovie()
    }

    fun saveMovie(movie: Movie) = viewModelScope.launch {
        movieRepository.upsertMovie(movie)
    }

    fun getSavedMovies() = movieRepository.getSavedMovie()

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        movieRepository.deleteMovie(movie)
    }

    fun getPopularMovie() = viewModelScope.launch {
        safePopularMovieCall()
    }

    private suspend fun safePopularMovieCall() {
        popularMovie.postValue(Resource.Loading())
        try {
            val response = movieRepository.getPopularMovies(API_KEY, popularMoviePage)
            popularMovie.postValue(handleMovieResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> popularMovie.postValue(Resource.Error("Network failure"))
                else -> popularMovie.postValue(Resource.Error("Conversion error"))
            }
        }
    }

    private fun handleMovieResponse(response: Response<MovieResponse>): Resource<MovieResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                popularMoviePage++
                if (movieResponse == null) {
                    movieResponse = result
                } else {
                    val oldMovies = movieResponse?.results
                    val newMovies = result.results
                    oldMovies?.addAll(newMovies)
                }
                return Resource.Success(movieResponse ?: result)
            }
        }
        return Resource.Error(response.message())
    }

    fun searchMovie(searchQuery: String) = viewModelScope.launch {
        safeSearchMovieResponse(searchQuery)
    }

    private suspend fun safeSearchMovieResponse(searchQuery: String) {
        searchMovie.postValue(Resource.Loading())
        try {
            val response = movieRepository.searchMovie(API_KEY, searchQuery, searchMoviePage)
            searchMovie.postValue(handleSearchResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchMovie.postValue(Resource.Error("Network failure"))
                else -> searchMovie.postValue(Resource.Error("Conversion error"))
            }
        }
    }

    private fun handleSearchResponse(response: Response<MovieResponse>): Resource<MovieResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                searchMoviePage++
                if (searchResponse == null) {
                    searchResponse = result
                } else {
                    val oldMovies = searchResponse?.results
                    val newMovies = result.results
                    oldMovies?.addAll(newMovies)
                }
                return Resource.Success(searchResponse ?: result)
            }
        }
        return Resource.Error(response.message())
    }
}