package com.example.mvvmhilt.domain.repos

import com.anushka.tmdbclient.data.model.tvshow.TvShow

interface ShowsRepo {
    suspend fun getTvShows():List<TvShow>?
    suspend fun updateTvShows():List<TvShow>?
}