package com.example.mvvmhilt.apis

import com.example.mvvmhilt.models.Resource
import com.example.mvvmhilt.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * REST API access points
 */
interface ModuleSpecificApis {

    //simple get example
    @GET("astros.json")
    suspend fun getUsers(): Response<UserResponse>
}
