package com.example.mvvmhilt.data.repos.tvshow

import com.example.mvvmhilt.data.models.DataState
import com.example.mvvmhilt.data.models.tvshow.TvShowList

interface ShowsRepo {
    suspend fun getTvShows(): DataState<TvShowList>?
    suspend fun deleteShows()
}