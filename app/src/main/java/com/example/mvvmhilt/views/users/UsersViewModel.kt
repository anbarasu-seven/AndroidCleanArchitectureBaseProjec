package com.example.mvvmhilt.views.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.models.User
import com.example.mvvmhilt.data.models.UserResponse
import com.example.mvvmhilt.domain.usecase.DisplayUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val useCase: DisplayUsersUseCase) : ViewModel() {

    //Get live network data records from room DB as Flow
    private var _roomUsersData = MutableLiveData<List<User>>()
    val roomUsersData: LiveData<List<User>> = _roomUsersData

    //Get data from web as live data and expose to view for observing
    private val _apiUsersData = MutableLiveData<UiState<UserResponse>>()
    val apiUsersData: LiveData<UiState<UserResponse>> = _apiUsersData

    init {
        _roomUsersData = useCase.observeRoomUsersData() as MutableLiveData<List<User>>
    }
    /**
     * Launch network request to fetch data
     */
    fun performNetworkRequest() = viewModelScope.launch {
        _apiUsersData.postValue(UiState.Loading())
        val response = useCase.getUsersFromApi()
        _apiUsersData.postValue(response)
    }

    /**
     * Insert data into room DB
     */
    fun insertData(data: ArrayList<User>) = viewModelScope.launch(Dispatchers.IO) {
        useCase.insert(data)
    }

    /**
     * Delete data from room DB
     */
    fun deleteData() = viewModelScope.launch(Dispatchers.IO) {
        useCase.clear()
    }
}