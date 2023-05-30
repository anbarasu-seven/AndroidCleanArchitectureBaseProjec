package com.example.mvvmhilt.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmhilt.data.models.Resource
import com.example.mvvmhilt.data.models.User
import com.example.mvvmhilt.data.models.UserResponse
import com.example.mvvmhilt.domain.repos.UsersRepo

class FakeSampleRepoImpl : UsersRepo {

    //observable user list from DB
    val observableNetworkError = MutableLiveData<String>()
    private val usersList = mutableListOf<User>()

    //observable user list from DB
    val observableUsersList: MutableLiveData<List<User>> = MutableLiveData(usersList)

    //error handling for nw request
    var shouldReturnNetworkError = false

    fun shouldReturnNetworkError(status: Boolean) {
        this.shouldReturnNetworkError = status
    }

    override suspend fun insert(data: ArrayList<User>) {
        usersList.addAll(data)
        refreshLiveData()
    }

    override suspend fun clear() {
        usersList.clear()
        refreshLiveData()
    }

    override suspend fun getUsersFromApi(): Resource<UserResponse> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Network error")
        } else {
            val response = UserResponse(
                message = "Success",
                1,
                people = arrayListOf(User("Name1", "Craft1"))
            )
            Resource.Success(response)
        }

    }

    override fun observeRoomUsersData(): LiveData<List<User>> = observableUsersList

    suspend fun observeNetworkError(): MutableLiveData<String> = observableNetworkError

    suspend fun getAllUsersFromDb() {
        usersList.clear()
        usersList.add(User("Name1", "Craft1"))
        usersList.add(User("Name2", "Craft2"))
        refreshLiveData()
    }

    private fun refreshLiveData() {
        observableUsersList.postValue(usersList)
    }
}