package com.example.mvvmhilt.domain.usecase

import com.anushka.tmdbclient.data.model.tvshow.ShowsResponse
import com.anushka.tmdbclient.data.model.tvshow.TvShow
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.domain.repos.ShowsRepo
import javax.inject.Inject

class UpdateTvShowsUseCase @Inject constructor(private val tvShowRepository: ShowsRepo) {
    suspend fun execute(): UiState<ShowsResponse>? = tvShowRepository.updateTvShows()
}