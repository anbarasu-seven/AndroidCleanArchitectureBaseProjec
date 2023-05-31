package com.example.mvvmhilt.data.repos.tvshow

import com.anushka.tmdbclient.data.model.tvshow.ShowsResponse
import com.anushka.tmdbclient.data.model.tvshow.TvShow
import com.example.mvvmhilt.common.utils.InternetStatus
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowCacheDataSource
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowLocalDataSource
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowRemoteDatasource
import com.example.mvvmhilt.domain.repos.ShowsRepo
import timber.log.Timber
import javax.inject.Inject

class ShowsRepositoryImpl @Inject constructor(
    private val tvShowRemoteDatasource: TvShowRemoteDatasource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowCacheDataSource: TvShowCacheDataSource,
    private val internetStatus: InternetStatus
) : ShowsRepo {

    override suspend fun getTvShows(): UiState<ShowsResponse>? {
        val result = if (internetStatus.isConnected()) {
            updateTvShows()
        } else {
            getTvShowsFromDB()
        }
        return result
    }

    override suspend fun updateTvShows(): UiState<ShowsResponse>? {
        val newListOfTvShows = getTvShowsFromAPI()
        tvShowLocalDataSource.clearAll()
        newListOfTvShows.data?.apply {
            tvShowLocalDataSource.saveTvShowsToDB(shows)
            tvShowCacheDataSource.saveTvShowsToCache(shows)
        }
        return newListOfTvShows
    }

    suspend fun getTvShowsFromAPI(): UiState<ShowsResponse> {
        return tvShowRemoteDatasource.getTvShows()
    }

    suspend fun getTvShowsFromDB(): UiState<ShowsResponse> {
        lateinit var tvShowsList: List<TvShow>
        try {
            tvShowsList = tvShowLocalDataSource.getTvShowsFromDB()
        } catch (exception: Exception) {
            Timber.tag("MyTag").i(exception.message.toString())
        }
        return UiState.Success(ShowsResponse(tvShowsList))
    }

    suspend fun getTvShowsFromCache(): UiState<ShowsResponse> {
        lateinit var tvShowsList: List<TvShow>
        try {
            tvShowsList = tvShowCacheDataSource.getTvShowsFromCache()
        } catch (exception: Exception) {
            Timber.tag("MyTag").i(exception.message.toString())
        }
        return UiState.Success(ShowsResponse(tvShowsList))
    }


}