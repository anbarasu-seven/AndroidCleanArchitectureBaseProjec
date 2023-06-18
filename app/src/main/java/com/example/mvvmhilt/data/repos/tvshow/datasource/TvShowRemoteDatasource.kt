package com.example.mvvmhilt.data.repos.tvshow.datasource

import com.example.mvvmhilt.data.models.tvshow.TvShowList
import com.example.mvvmhilt.data.setups.api.TMDBService
import com.example.mvvmhilt.data.models.Config
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.repos.BaseRemoteData
import javax.inject.Inject
import javax.inject.Singleton

//container to fetch fresh data from remote data source
@Singleton
class TvShowRemoteDatasource @Inject constructor(
    private val tmdbService: TMDBService,
    private val config: Config
) : BaseRemoteData() {

    suspend fun getTvShows()
            : UiState<TvShowList> {
        val result = safeApiCall { tmdbService.getPopularTvShows(config.API_KEY) }
        return result
    }

}