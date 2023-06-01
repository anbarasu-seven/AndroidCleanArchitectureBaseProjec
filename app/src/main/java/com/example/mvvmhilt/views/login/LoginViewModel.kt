package com.example.mvvmhilt.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmhilt.common.utils.Validator
import com.example.mvvmhilt.data.models.AuthInfo
import com.example.mvvmhilt.data.models.Event
import com.example.mvvmhilt.data.models.Route
import com.example.mvvmhilt.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validator: Validator
) : ViewModel() {

    private val _errorData = MutableLiveData<Event<String>>()
    val errorData: LiveData<Event<String>> = _errorData

    private val _navigate = MutableLiveData<Event<String>>()
    var navigate: LiveData<Event<String>> = _navigate

    fun validate(username: String, password: String) {
        val status = validator.validateLoginInput(username, password)
        if (!status) {
            _errorData.postValue(Event("Username should be 6 char, Password should be 6 char and might include 2 digit"))
            return
        }
        viewModelScope.launch {
            val result = async { loginUseCase.execute(AuthInfo(username, password)) }.await()
            if (result) {
                _navigate.postValue(Event(Route.SHOWS))
            }
        }
    }

}