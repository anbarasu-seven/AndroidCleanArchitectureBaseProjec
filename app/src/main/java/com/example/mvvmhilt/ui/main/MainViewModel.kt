package com.example.mvvmhilt.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmhilt.ui.support.Route
import com.example.mvvmhilt.domain.usecase.LogoutUseCase
import com.example.mvvmhilt.domain.usecase.VerifyLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val verifyLoginUseCase: VerifyLoginUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    // Inside your ViewModel
    private val _navigate = MutableLiveData<String>()
    val navigate: LiveData<String> = _navigate

    // Inside your ViewModel
    private val _pageTitle = MutableLiveData<String>()
    val pageTitle: LiveData<String> = _pageTitle

    // Inside your ViewModel
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun navigateTo(page: String) {
        _navigate.postValue(page)
    }

    fun chageTitle(title: String) {
        _pageTitle.postValue(title)
    }

    fun verifyLoggin() {
        viewModelScope.launch {
            val result = async { verifyLoginUseCase.execute() }.await()
            _isLoggedIn.postValue(result)
        }
    }

    fun logout() {
        viewModelScope.launch {
            val result = async { logoutUseCase.execute() }.await()
            if (result) _navigate.postValue(Route.LOGIN)
        }
    }
}