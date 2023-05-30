package com.example.mvvmhilt.domain.usecase

import com.example.mvvmhilt.data.models.Resource
import com.example.mvvmhilt.data.models.User
import com.example.mvvmhilt.data.models.UserResponse
import com.example.mvvmhilt.domain.repos.UsersRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DisplayUsersUseCase @Inject constructor(private val usersRepo: UsersRepo) {

    suspend fun insert(data: ArrayList<User>) = usersRepo.insert(data)

    suspend fun clear() = usersRepo.clear()

    suspend fun getUsersFromApi(): Resource<UserResponse> = usersRepo.getUsersFromApi()

    fun observeRoomUsersData() = usersRepo.observeRoomUsersData()
}