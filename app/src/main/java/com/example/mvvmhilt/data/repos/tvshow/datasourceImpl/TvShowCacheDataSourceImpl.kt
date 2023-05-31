package com.example.mvvmhilt.data.repos.tvshow.datasourceImpl

import com.anushka.tmdbclient.data.model.tvshow.TvShow
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowCacheDataSource
import javax.inject.Inject

class TvShowCacheDataSourceImpl @Inject constructor() :
    TvShowCacheDataSource {
    private var tvShowList = ArrayList<TvShow>()

    override suspend fun getTvShowsFromCache(): List<TvShow> {
        return tvShowList
    }

    override suspend fun saveTvShowsToCache(tvShows: List<TvShow>) {
        tvShowList.clear()
        tvShowList = ArrayList(tvShows)
    }
}