package com.example.mvvmhilt.domain.usecase

import com.anushka.tmdbclient.data.model.tvshow.TvShowList
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.domain.repos.ShowsRepo
import javax.inject.Inject

class GetShowsUseCase @Inject constructor(private val tvShowRepository: ShowsRepo) {
    suspend fun execute(): UiState<TvShowList>? = tvShowRepository.getTvShows()
}