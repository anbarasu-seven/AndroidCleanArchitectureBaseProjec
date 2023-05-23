package com.example.mvvmhilt.viewmodels

import androidx.lifecycle.*
import com.example.mvvmhilt.models.NetworkData
import com.example.mvvmhilt.models.UserResponse
import com.example.mvvmhilt.repository.SampleRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(private val sampleRepo: SampleRepo) : ViewModel() {

    //Get live network data records from room DB as Flow
    val errorData: LiveData<String?> = sampleRepo.errorData

    //Get live network data records from room DB as Flow
    val networkData: LiveData<List<NetworkData>> = sampleRepo.allData.asLiveData()

    //Get data from web as live data and expose to view for observing
    val webData: MutableLiveData<UserResponse?> = sampleRepo.webData

    /**
     * Launch network request to fetch data
     */
    fun performNetworkRequest() = viewModelScope.launch {
        sampleRepo.performRetrofitRequest()
    }

    /**
     * Insert data into room DB
     */
    fun insertData(data: ArrayList<NetworkData>) = viewModelScope.launch(Dispatchers.IO) {
        sampleRepo.insertData(data)
    }

    /**
     * Delete data from room DB
     */
    fun deleteData() = viewModelScope.launch(Dispatchers.IO) {
        sampleRepo.clearAll()
    }
}