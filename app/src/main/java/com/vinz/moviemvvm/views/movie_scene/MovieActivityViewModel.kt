package com.vinz.moviemvvm.views.movie_scene

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vinz.moviemvvm.data.repository.NetworkState
import com.vinz.moviemvvm.data.entities.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieActivityViewModel (private val movieRepository: MovieRepository, movieId: Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}