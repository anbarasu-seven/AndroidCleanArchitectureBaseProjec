package com.example.mvvmhilt.views.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmhilt.data.models.Resource
import com.example.mvvmhilt.data.models.User
import com.example.mvvmhilt.data.models.UserResponse
import com.example.mvvmhilt.repos.SampleRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val sampleRepo: SampleRepo) : ViewModel() {

    //Get live network data records from room DB as Flow
    var roomUsersData: LiveData<List<User>> = MutableLiveData()

    //Get data from web as live data and expose to view for observing
    val apiUsersData: MutableLiveData<Resource<UserResponse>> = MutableLiveData()

    init {
        roomUsersData = sampleRepo.observeRoomUsersData()
    }
    /**
     * Launch network request to fetch data
     */
    fun performNetworkRequest() = viewModelScope.launch {
        val response = sampleRepo.getUsersFromApi()
        apiUsersData.postValue(response)
    }

    /**
     * Insert data into room DB
     */
    fun insertData(data: ArrayList<User>) = viewModelScope.launch(Dispatchers.IO) {
        sampleRepo.insert(data)
    }

    /**
     * Delete data from room DB
     */
    fun deleteData() = viewModelScope.launch(Dispatchers.IO) {
        sampleRepo.clear()
    }
}