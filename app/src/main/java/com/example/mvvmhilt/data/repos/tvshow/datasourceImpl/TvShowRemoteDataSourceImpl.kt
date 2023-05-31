package com.example.mvvmhilt.data.repos.tvshow.datasourceImpl

import com.anushka.tmdbclient.data.model.tvshow.ShowsResponse
import com.example.mvvmhilt.data.api.TMDBService
import com.example.mvvmhilt.data.models.Config
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.repos.BaseRepo
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowRemoteDatasource
import javax.inject.Inject

class TvShowRemoteDataSourceImpl @Inject constructor(
    private val tmdbService: TMDBService,
    private val config: Config
) : BaseRepo(), TvShowRemoteDatasource {
    override suspend fun getTvShows()
            : UiState<ShowsResponse> = safeApiCall { tmdbService.getPopularTvShows(config.API_KEY) }

}

