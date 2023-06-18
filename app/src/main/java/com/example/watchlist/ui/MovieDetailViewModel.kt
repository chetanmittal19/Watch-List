package com.example.watchlist.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.watchlist.data.Movie
import com.example.watchlist.data.MovieDetailRepository

class MovieDetailViewModel(id: Long, application: Application): ViewModel(){
    private val repo: MovieDetailRepository = MovieDetailRepository(application)
    val movie: LiveData<Movie> = repo.getMovie(id)
}