package com.example.watchlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.watchlist.R
import com.example.watchlist.data.Movie
import com.example.watchlist.data.network.TmdbService
import com.example.watchlist.databinding.FragmentMovieDetailBinding
import com.example.watchlist.readableFormat

class MovieDetailFragment : Fragment() {
    private lateinit var _binding: FragmentMovieDetailBinding
    private val binding get() = _binding
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val id: Long = MovieDetailFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = MovieDetailViewModelFactory(id, requireActivity().application)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[MovieDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.movie.observe(viewLifecycleOwner) {
            setData(it)
        }
    }

    private fun setData(movie: Movie){
        Glide.with(requireActivity())
            .load(TmdbService.POSTER_BASE_URL + movie.posterPath)
            .error(R.drawable.poster_placeholder)
            .into(binding.moviePoster)

        Glide.with(requireActivity())
            .load(TmdbService.BACKDROP_BASE_URL + movie.backdropPath)
            .into(binding.movieBackdrop)

        binding.movieTitle.text = movie.title
        binding.movieOverview.text = movie.overview

        binding.movieReleaseDate.text = movie.releaseDate.readableFormat()
    }
}
