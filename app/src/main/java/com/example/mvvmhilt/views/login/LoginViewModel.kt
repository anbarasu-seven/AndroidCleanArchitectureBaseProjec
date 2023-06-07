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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validator: Validator
) : ViewModel() {

    private val _errorData = MutableSharedFlow<String>()
    val errorData: SharedFlow<String> = _errorData

    private val _navigate = MutableSharedFlow<String>()
    var navigate: SharedFlow<String> = _navigate

    fun validate(username: String, password: String) {
        viewModelScope.launch {
            val status = validator.validateLoginInput(username, password)
            if(!status){
                _errorData.emit("Username should be 6 char, Password should be 6 char and must include 2 digit")
            }else{
                val result = async { loginUseCase.execute(AuthInfo(username, password)) }.await()
                if (result) {
                    _navigate.emit(Route.SHOWS)
                }
            }
        }
    }

}