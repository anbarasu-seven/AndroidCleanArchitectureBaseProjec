package com.example.mvvmhilt.domain.repos

import com.anushka.tmdbclient.data.model.tvshow.TvShowList
import com.example.mvvmhilt.data.models.UiState

interface ShowsRepo {
    suspend fun getTvShows(): UiState<TvShowList>?
    suspend fun deleteShows(): Unit
}