package com.example.mvvmhilt.data.repos.tvshow

import com.anushka.tmdbclient.data.model.tvshow.TvShow
import com.anushka.tmdbclient.data.model.tvshow.TvShowList
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowCacheDataSource
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowLocalDataSource
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowRemoteDatasource
import com.example.mvvmhilt.domain.repos.ShowsRepo
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowsRepositoryImpl @Inject constructor(
    private val tvShowRemoteDatasource: TvShowRemoteDatasource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowCacheDataSource: TvShowCacheDataSource,
) : ShowsRepo {

    //get tv shows generic
    override suspend fun getTvShows(): UiState<TvShowList>? = getTvShowsFromAPI()

    //delete local copy of tv shows
    override suspend fun deleteShows(): Unit {
        tvShowLocalDataSource.clearAll()
        tvShowCacheDataSource.clearAll()
    }

    //retrieve shows from remote data source
    suspend fun getTvShowsFromAPI(): UiState<TvShowList> {
        val results = tvShowRemoteDatasource.getTvShows()
        results.data?.let {
            it.let { list ->
                tvShowLocalDataSource.saveTvShowsToDB(it.tvShows)
                tvShowCacheDataSource.saveTvShowsToCache(it.tvShows)
            }

        }
        return results
    }

    //retrieve shows from remote data source, if internet is not available
    suspend fun getTvShowsFromDB(): UiState<TvShowList> {
        lateinit var tvShowsList: List<TvShow>
        try {
            tvShowsList = tvShowLocalDataSource.getTvShowsFromDB()
        } catch (exception: Exception) {
            Timber.tag("MyTag").i(exception.message.toString())
        }
        return UiState.Success(TvShowList(tvShowsList))
    }

    //local copy of same tv shows list, app level runtime scoped cache
    suspend fun getTvShowsFromCache(): UiState<TvShowList> {
        lateinit var tvShowsList: List<TvShow>
        try {
            tvShowsList = tvShowCacheDataSource.getTvShowsFromCache()
        } catch (exception: Exception) {
            Timber.tag("MyTag").i(exception.message.toString())
        }
        return UiState.Success(TvShowList(tvShowsList))
    }


}