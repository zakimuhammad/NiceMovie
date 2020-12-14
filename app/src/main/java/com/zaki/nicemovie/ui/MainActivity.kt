package com.zaki.nicemovie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.zaki.nicemovie.R
import com.zaki.nicemovie.db.MovieDatabase
import com.zaki.nicemovie.repository.MovieRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieRepository = MovieRepository(MovieDatabase(this))
        val movieViewModelProviderFactory = MovieViewModelProviderFactory(application, movieRepository)
        viewModel = ViewModelProvider(this, movieViewModelProviderFactory).get(MovieViewModel::class.java)

        bottomNavigationView.setupWithNavController(movieNavHostFragment.findNavController())
    }
}