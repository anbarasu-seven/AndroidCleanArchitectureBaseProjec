package com.example.mvvmhilt.domain.repos

import com.anushka.tmdbclient.data.model.tvshow.ShowsResponse
import com.example.mvvmhilt.data.models.UiState

interface ShowsRepo {
    suspend fun getTvShows(): UiState<ShowsResponse>?
    suspend fun updateTvShows(): UiState<ShowsResponse>?
}