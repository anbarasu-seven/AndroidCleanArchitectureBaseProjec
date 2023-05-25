package com.example.mvvmhilt.repos

import com.example.mvvmhilt.data.api.ModuleSpecificApis
import com.example.mvvmhilt.data.models.Resource
import com.example.mvvmhilt.data.models.UserData
import com.example.mvvmhilt.data.models.UserResponse
import com.example.mvvmhilt.data.room.SampleDao
import javax.inject.Inject

class SampleRepoImpl @Inject constructor(
    private val api: ModuleSpecificApis,
    private val dao: SampleDao
) : BaseRepo(), SampleRepo {

    override suspend fun insert(data: ArrayList<UserData>) = dao.insert(data)

    override suspend fun clear() = dao.clear()

    override suspend fun getUsersFromApi(): Resource<UserResponse> = safeApiCall { api.getUsers() }

    override fun observeRoomUsersData() = dao.getUsers()

}