package com.example.mvvmhilt.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmhilt.common.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    val errorData: LiveData<String> = MutableLiveData()
    var navigate: LiveData<Boolean> = MutableLiveData()

    fun validate(username: String, password: String) {
        val status = Validator.validateLoginInput(username, password)
        if (status) (navigate as MutableLiveData).postValue(true)
        else (errorData as MutableLiveData).postValue("Username and password error")
    }

}