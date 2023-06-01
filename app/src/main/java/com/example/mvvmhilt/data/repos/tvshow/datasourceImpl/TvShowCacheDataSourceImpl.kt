package com.example.mvvmhilt.data.repos.tvshow.datasourceImpl

import com.anushka.tmdbclient.data.model.tvshow.TvShow
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowCacheDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowCacheDataSourceImpl @Inject constructor() :
    TvShowCacheDataSource {
    private var tvShowList = ArrayList<TvShow>()

    override suspend fun getTvShowsFromCache(): List<TvShow> {
        return tvShowList
    }

    override suspend fun saveTvShowsToCache(tvShows: List<TvShow>) {
        if(tvShows != null) {
            tvShowList.clear()
            tvShowList = ArrayList(tvShows)
        }
    }

    override suspend fun clearAll() {
        tvShowList.clear()
    }
}