package com.example.mvvmhilt.data.repos.tvshow.datasource

import com.anushka.tmdbclient.data.model.tvshow.TvShow

//container to back up data at runtime
interface TvShowCacheDataSource {
    suspend fun getTvShowsFromCache():List<TvShow>
    suspend fun saveTvShowsToCache(tvShows:List<TvShow>)
    suspend fun clearAll()
}