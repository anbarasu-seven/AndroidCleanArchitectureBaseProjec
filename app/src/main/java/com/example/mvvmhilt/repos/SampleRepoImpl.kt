package com.example.mvvmhilt.repos

import com.example.mvvmhilt.data.api.UsersApi
import com.example.mvvmhilt.data.models.Resource
import com.example.mvvmhilt.data.models.User
import com.example.mvvmhilt.data.models.UserResponse
import com.example.mvvmhilt.data.room.UserDao
import javax.inject.Inject

class SampleRepoImpl @Inject constructor(
    private val api: UsersApi,
    private val dao: UserDao
) : BaseRepo(), SampleRepo {

    override suspend fun insert(data: ArrayList<User>) = dao.insert(data)

    override suspend fun clear() = dao.clear()

    override suspend fun getUsersFromApi(): Resource<UserResponse> = safeApiCall { api.getUsers() }

    override fun observeRoomUsersData() = dao.getUsers()

}