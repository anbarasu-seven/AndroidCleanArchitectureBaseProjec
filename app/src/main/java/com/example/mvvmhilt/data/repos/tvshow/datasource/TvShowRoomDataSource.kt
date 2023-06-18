package com.example.mvvmhilt.data.repos.tvshow.datasource

import com.example.mvvmhilt.data.models.tvshow.TvShow
import com.example.mvvmhilt.data.setups.room.TvShowDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowRoomDataSource @Inject constructor(private val tvDao: TvShowDao) {
    suspend fun getTvShowsFromDB(): List<TvShow> {
        return tvDao.getTvShows()
    }

    suspend fun saveTvShowsToDB(tvShows: List<TvShow>?) {
        tvShows?.let {
            tvDao.saveTvShows(tvShows)
        }
//    CoroutineScope(Dispatchers.IO).launch {
//      tvShows?.let {
//        tvDao.saveTvShows(tvShows)
//      }
//    }
    }

    suspend fun clearAll() {
        tvDao.deleteAllTvShows()
//    CoroutineScope(Dispatchers.IO).launch {
//      tvDao.deleteAllTvShows()
//    }
    }
}