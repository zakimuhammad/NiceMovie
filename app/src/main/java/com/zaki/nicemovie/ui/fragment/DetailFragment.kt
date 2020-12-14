package com.zaki.nicemovie.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.zaki.nicemovie.R
import com.zaki.nicemovie.ui.MainActivity
import com.zaki.nicemovie.ui.MovieViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_movie.view.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    lateinit var viewModel: MovieViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        val movie = args.movie

        Glide.with(this).load(movie.backdropPath).into(img_movie_detail)
        tv_title_detail.text = movie.title
        tv_desc_detail.text = movie.overview
        tv_date.text = movie.releaseDate
        tv_star.text = movie.voteAverage.toString()

        fab.setOnClickListener {
            viewModel.saveMovie(movie)
            Snackbar.make(view, "Movie saved succesfully", Snackbar.LENGTH_LONG).show()
        }
    }
}