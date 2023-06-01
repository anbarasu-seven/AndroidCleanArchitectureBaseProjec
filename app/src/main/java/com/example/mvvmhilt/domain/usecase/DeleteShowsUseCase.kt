package com.example.mvvmhilt.domain.usecase

import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.domain.repos.ShowsRepo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DeleteShowsUseCase @Inject constructor(private val tvShowRepository: ShowsRepo) {
    suspend fun execute(): Unit = tvShowRepository.deleteShows()
}