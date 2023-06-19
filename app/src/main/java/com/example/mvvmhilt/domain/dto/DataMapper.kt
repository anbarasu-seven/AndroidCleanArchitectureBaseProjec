package com.example.mvvmhilt.domain.dto

import com.example.mvvmhilt.data.models.AuthInfo
import com.example.mvvmhilt.data.models.DataState
import com.example.mvvmhilt.data.models.tvshow.TvShowList

class DataMapper {

    companion object {
        fun getTvShowListDto(shows: DataState<TvShowList>?): UIState<TvShowListDto> {
            //data mapping
            return if (shows is DataState.Loading) {
                UIState.Loading()
            } else if (shows is DataState.Error) {
                UIState.Error(shows.message ?: "")
            } else {
                val showList = shows?.data?.tvShows?.map { show ->
                    TvShowDto(
                        show.firstAirDate,
                        show.id,
                        show.name,
                        show.overview,
                        show.posterPath
                    )
                }
                UIState.Success(TvShowListDto(showList ?: ArrayList(0)))
            }
        }

        fun toAuthInfo(authInfoDto: AuthInfoDto): AuthInfo =
            AuthInfo(authInfoDto.name, authInfoDto.password)


    }

}