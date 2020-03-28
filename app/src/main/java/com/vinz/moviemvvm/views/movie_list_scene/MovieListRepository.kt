package com.vinz.moviemvvm.views.movie_list_scene

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vinz.moviemvvm.data.api.POST_PER_PAGE
import com.vinz.moviemvvm.data.api.TheMovieDBInterface
import com.vinz.moviemvvm.data.repository.MovieDataSource
import com.vinz.moviemvvm.data.repository.MovieDataSourceFactory
import com.vinz.moviemvvm.data.repository.NetworkState
import com.vinz.moviemvvm.data.entities.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieListRepository (private val apiService: TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        moviesDataSourceFactory =
            MovieDataSourceFactory(
                apiService,
                compositeDisposable
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}