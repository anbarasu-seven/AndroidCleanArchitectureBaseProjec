package com.example.mvvmhilt.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmhilt.data.models.Resource
import com.example.mvvmhilt.data.models.UserData
import com.example.mvvmhilt.data.models.UserResponse

class FakeSampleRepoImpl : SampleRepo {

    //observable user list from DB
    val observableNetworkError = MutableLiveData<String>()
    private val usersList = mutableListOf<UserData>()

    //observable user list from DB
    val observableUsersList: MutableLiveData<List<UserData>> = MutableLiveData(usersList)

    //error handling for nw request
    var shouldReturnNetworkError = false

    fun shouldReturnNetworkError(status: Boolean) {
        this.shouldReturnNetworkError = status
    }

    override suspend fun insert(data: ArrayList<UserData>) {
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
                people = arrayListOf(UserData("Name1", "Craft1"))
            )
            Resource.Success(response)
        }

    }

    override fun observeRoomUsersData(): LiveData<List<UserData>> = observableUsersList

    suspend fun observeNetworkError(): MutableLiveData<String> = observableNetworkError

    suspend fun getAllUsersFromDb() {
        usersList.clear()
        usersList.add(UserData("Name1", "Craft1"))
        usersList.add(UserData("Name2", "Craft2"))
        refreshLiveData()
    }

    private fun refreshLiveData() {
        observableUsersList.postValue(usersList)
    }
}