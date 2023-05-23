package com.example.mvvmhilt.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmhilt.apis.ModuleSpecificApis
import com.example.mvvmhilt.interfaces.SampleDao
import com.example.mvvmhilt.models.NetworkData
import com.example.mvvmhilt.models.Resource
import com.example.mvvmhilt.models.UserResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SampleRepo @Inject constructor(
    private val api: ModuleSpecificApis,
    private val dao: SampleDao
) : BaseRepo() {

    val errorData: MutableLiveData<String?> = MutableLiveData()

    //Stream live data as Flow
    val allData: Flow<List<NetworkData>> = dao.getOrderedNetworkDataFlow()

    //To store webData received from the network
    val webData = MutableLiveData<UserResponse?>()

    /**
     * This function insert network data to DB
     */
    fun insertData(data: ArrayList<NetworkData>) {
        dao.insert(data)
    }

    /**
     * This function deletes data from table
     */
    fun clearAll() {
        dao.clearAll()
    }

    suspend fun performRetrofitRequest() {
        // Passing 'api.fetchPopularArticles()' function
        // as an argument in safeApiCall function
        val response: Resource<UserResponse> = safeApiCall { api.getUsers() }

        if (response is Resource.Success) {
            webData.postValue(response.data)
        } else if (response is Resource.Error) {
            val error = response as Resource.Error
            errorData.postValue(error.message)
        }
    }
}