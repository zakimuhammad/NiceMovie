package com.zaki.nicemovie.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaki.nicemovie.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class MovieViewModelProviderFactory(
    val app: Application,
    private val movieRepository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(app, movieRepository) as T
    }
}