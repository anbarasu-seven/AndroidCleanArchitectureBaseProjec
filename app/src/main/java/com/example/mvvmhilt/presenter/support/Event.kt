package com.example.mvvmhilt.presenter.support

class Event<T>(private val content: T) {

    var isHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return takeIf { !isHandled }?.let {
            isHandled = true
            content
        }
    }
}