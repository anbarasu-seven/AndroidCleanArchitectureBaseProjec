package com.example.mvvmhilt.domain.dto

import com.example.mvvmhilt.data.models.tvshow.TvShow

data class TvShowDto(
    val firstAirDate: String,
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String
)

fun TvShowDto.toToShow(): TvShow = TvShow(firstAirDate, id, name, overview, posterPath)
