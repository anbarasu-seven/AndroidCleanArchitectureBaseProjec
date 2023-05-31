package com.example.mvvmhilt.data.repos.tvshow.datasource

import com.anushka.tmdbclient.data.model.movie.MovieList
import com.anushka.tmdbclient.data.model.tvshow.ShowsResponse
import com.anushka.tmdbclient.data.model.tvshow.TvShowList
import com.example.mvvmhilt.data.models.UiState
import retrofit2.Response

//container to fetch fresh data from remote data source
interface TvShowRemoteDatasource {
   suspend fun getTvShows(): UiState<ShowsResponse>
}