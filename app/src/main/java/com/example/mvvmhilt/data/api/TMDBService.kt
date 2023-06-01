package com.example.mvvmhilt.data.api

import com.anushka.tmdbclient.data.model.tvshow.TvShowList
import com.example.mvvmhilt.BuildConfig.PATH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    @GET("$PATH")
    suspend fun getPopularTvShows(
        @Query(
            "api_key"
        ) apiKey: String
    ): Response<TvShowList>

}