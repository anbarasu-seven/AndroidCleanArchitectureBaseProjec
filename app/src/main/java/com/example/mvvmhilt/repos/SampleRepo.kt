package com.example.mvvmhilt.repos

import androidx.lifecycle.LiveData
import com.example.mvvmhilt.data.models.Resource
import com.example.mvvmhilt.data.models.UserData
import com.example.mvvmhilt.data.models.UserResponse

interface SampleRepo {
    suspend fun insert(data: ArrayList<UserData>)
    suspend fun clear()
    suspend fun getUsersFromApi(): Resource<UserResponse>
    fun observeRoomUsersData(): LiveData<List<UserData>>
}