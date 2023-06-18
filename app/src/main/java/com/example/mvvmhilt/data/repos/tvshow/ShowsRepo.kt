package com.example.mvvmhilt.data.repos.tvshow

import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.models.tvshow.TvShowList

interface ShowsRepo {
    suspend fun getTvShows(): UiState<TvShowList>?
    suspend fun deleteShows()
}