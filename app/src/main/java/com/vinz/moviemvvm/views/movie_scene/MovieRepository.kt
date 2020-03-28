package com.vinz.moviemvvm.views.movie_scene

import androidx.lifecycle.LiveData
import com.vinz.moviemvvm.data.api.TheMovieDBInterface
import com.vinz.moviemvvm.data.repository.MovieDetailsNetworkDataSource
import com.vinz.moviemvvm.data.repository.NetworkState
import com.vinz.moviemvvm.data.entities.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieRepository (private val apiService: TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails> {
        movieDetailsNetworkDataSource =
            MovieDetailsNetworkDataSource(
                apiService,
                compositeDisposable
            )
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}