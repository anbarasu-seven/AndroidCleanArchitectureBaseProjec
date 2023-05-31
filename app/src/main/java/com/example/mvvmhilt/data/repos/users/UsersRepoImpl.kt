package com.example.mvvmhilt.data.repos.users

import com.example.mvvmhilt.data.api.UsersApi
import com.example.mvvmhilt.data.models.Resource
import com.example.mvvmhilt.data.models.User
import com.example.mvvmhilt.data.models.UserResponse
import com.example.mvvmhilt.data.repos.BaseRepo
import com.example.mvvmhilt.data.room.UserDao
import com.example.mvvmhilt.domain.repos.UsersRepo
import javax.inject.Inject

class UsersRepoImpl @Inject constructor(
    private val api: UsersApi,
    private val dao: UserDao
) : BaseRepo(), UsersRepo {

    override suspend fun insert(data: ArrayList<User>) = dao.insert(data)

    override suspend fun clear() = dao.clear()

    override suspend fun getUsersFromApi(): Resource<UserResponse> = safeApiCall { api.getUsers() }

    override fun observeRoomUsersData() = dao.getUsers()

}