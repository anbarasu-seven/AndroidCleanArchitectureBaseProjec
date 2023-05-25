package com.example.mvvmhilt.views.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmhilt.data.models.Resource
import com.example.mvvmhilt.data.models.UserData
import com.example.mvvmhilt.data.models.UserResponse
import com.example.mvvmhilt.repos.SampleRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(private val sampleRepo: SampleRepoImpl) : ViewModel() {

    //Get live network data records from room DB as Flow
    var errorData: MutableLiveData<String?> = MutableLiveData()

    //Get live network data records from room DB as Flow
    var roomUsersData: LiveData<List<UserData>> = MutableLiveData()

    //Get data from web as live data and expose to view for observing
    val apiUsersData: MutableLiveData<UserResponse?> = MutableLiveData()

    init {
        roomUsersData = sampleRepo.observeRoomUsersData()
    }

    /**
     * Launch network request to fetch data
     */
    fun performNetworkRequest() = viewModelScope.launch {
        val response = sampleRepo.getUsersFromApi()
        if (response is Resource.Success) {
            apiUsersData.postValue(response.data)
        } else if (response is Resource.Error) {
            val error = response as Resource.Error
            errorData.postValue(error.message)
        }
    }

    /**
     * Insert data into room DB
     */
    fun insertData(data: ArrayList<UserData>) = viewModelScope.launch(Dispatchers.IO) {
        sampleRepo.insert(data)
    }

    /**
     * Delete data from room DB
     */
    fun deleteData() = viewModelScope.launch(Dispatchers.IO) {
        sampleRepo.clear()
    }
}