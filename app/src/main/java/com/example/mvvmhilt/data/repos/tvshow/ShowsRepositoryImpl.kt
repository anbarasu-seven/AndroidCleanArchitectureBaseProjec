package com.example.mvvmhilt.data.repos.tvshow

import com.example.mvvmhilt.data.models.tvshow.TvShow
import com.example.mvvmhilt.data.models.tvshow.TvShowList
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowRemoteDatasource
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowRoomDataSource
import com.example.mvvmhilt.domain.repos.ShowsRepo
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowsRepositoryImpl @Inject constructor(
    private val tvShowRemoteDatasource: TvShowRemoteDatasource,
    private val tvShowLocalDataSource: TvShowRoomDataSource,
) : ShowsRepo {

    //get tv shows generic
    override suspend fun getTvShows(): UiState<TvShowList>? = getTvShowsFromAPI()

    //delete local data copy of tv shows
    override suspend fun deleteShows(): Unit {
        tvShowLocalDataSource.clearAll()
    }

    //retrieve shows from remote data source
    suspend fun getTvShowsFromAPI(): UiState<TvShowList> {
        val results = tvShowRemoteDatasource.getTvShows()
        results.let {
            it.data?.let { list ->
                tvShowLocalDataSource.saveTvShowsToDB(list.tvShows)
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

}