package com.example.mvvmhilt.data.api

import com.example.mvvmhilt.data.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * REST API access points
 */
interface UsersApi {

    //simple get example
    @GET("astros.json")
    suspend fun getUsers(): Response<UserResponse>
}