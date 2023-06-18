package com.example.mvvmhilt.domain.usecase

import com.example.mvvmhilt.data.models.tvshow.TvShowList
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.repos.tvshow.ShowsRepo
import javax.inject.Inject

class GetShowsUseCase @Inject constructor(private val tvShowRepository: ShowsRepo) {
    suspend fun execute(): UiState<TvShowList>? = tvShowRepository.getTvShows()
}