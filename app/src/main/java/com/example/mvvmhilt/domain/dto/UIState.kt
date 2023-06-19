package com.example.mvvmhilt.domain.dto

sealed class UIState<T>(
    val data: T? = null,
    val message: String? = null
) {

    // We'll wrap our data in this 'Success'
    // class in case of success response from api
    class Success<T>(data: T) : UIState<T>(data = data)

    // We'll pass error message wrapped in this 'Error'
    // class to the UI in case of failure response
    class Error<T>(message: String) : UIState<T>(message = message)

    // We'll just pass object of this Loading
    // class, just before making an api call
    class Loading<T> : UIState<T>()
}
