package com.example.mvvmhilt.domain.repos

import androidx.lifecycle.LiveData
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.models.User
import com.example.mvvmhilt.data.models.UserResponse

interface UsersRepo {
    suspend fun insert(data: ArrayList<User>)
    suspend fun clear()
    suspend fun getUsersFromApi(): UiState<UserResponse>
    fun observeRoomUsersData(): LiveData<List<User>>
}