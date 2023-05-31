package com.anushka.tmdbclient.data.model.tvshow

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvvmhilt.data.models.User
import com.google.gson.annotations.SerializedName

@Entity(tableName = "popular_tvShows")
data class TvShow(
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String
)

data class ShowsResponse(val shows: List<TvShow>)