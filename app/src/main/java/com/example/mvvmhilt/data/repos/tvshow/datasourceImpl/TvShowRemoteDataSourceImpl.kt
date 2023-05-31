package com.example.mvvmhilt.data.repos.tvshow.datasourceImpl

import com.anushka.tmdbclient.data.model.tvshow.TvShowList
import com.example.mvvmhilt.data.api.TMDBService
import com.example.mvvmhilt.data.models.Config
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowRemoteDatasource
import retrofit2.Response
import javax.inject.Inject

class TvShowRemoteDataSourceImpl @Inject constructor(
    private val tmdbService: TMDBService,
    private val config: Config
) : TvShowRemoteDatasource {
    override suspend fun getTvShows()
            : Response<TvShowList> = tmdbService.getPopularTvShows(config.API_KEY)

}

