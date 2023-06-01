package com.example.mvvmhilt.data.repos.tvshow.datasource

import com.anushka.tmdbclient.data.model.tvshow.TvShow
import com.example.mvvmhilt.data.room.TvShowDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowRoomDataSource @Inject constructor (private val tvDao: TvShowDao){
  suspend fun getTvShowsFromDB(): List<TvShow> {
    return tvDao.getTvShows()
  }

  suspend fun saveTvShowsToDB(tvShows: List<TvShow>?) {
    CoroutineScope(Dispatchers.IO).launch {
      tvShows?.let {
        tvDao.saveTvShows(tvShows)
      }
    }
  }

  suspend fun clearAll() {
    CoroutineScope(Dispatchers.IO).launch {
      tvDao.deleteAllTvShows()
    }
  }
}