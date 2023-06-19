package com.example.mvvmhilt.domain.usecase

import com.example.mvvmhilt.data.models.DataState
import com.example.mvvmhilt.data.models.tvshow.TvShowList
import com.example.mvvmhilt.data.repos.tvshow.ShowsRepo
import com.example.mvvmhilt.domain.dto.DataMapper
import com.example.mvvmhilt.domain.dto.TvShowListDto
import com.example.mvvmhilt.domain.dto.UIState
import javax.inject.Inject

class GetShowsUseCase @Inject constructor(private val tvShowRepository: ShowsRepo) {
    suspend fun execute(): UIState<TvShowListDto> {
        val shows: DataState<TvShowList>? = tvShowRepository.getTvShows()
        //data mapping
        return DataMapper.getTvShowListDto(shows)
    }
}