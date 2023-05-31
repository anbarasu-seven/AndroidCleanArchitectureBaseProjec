package com.example.mvvmhilt.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmhilt.common.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String> = _errorData

    private val _navigate = MutableLiveData<Boolean>()
    var navigate: LiveData<Boolean> = _navigate

    fun validate(username: String, password: String) {
        val status = Validator.validateLoginInput(username, password)
        if (status) _navigate.postValue(true)
        else _errorData.postValue("Username and password error")
    }

}