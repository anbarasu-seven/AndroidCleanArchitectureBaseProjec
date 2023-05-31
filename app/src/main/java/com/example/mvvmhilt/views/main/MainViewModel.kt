package com.example.mvvmhilt.views.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    var navigate: LiveData<Boolean> = MutableLiveData()

    fun navigateTo() {
        (navigate as MutableLiveData ).postValue(true)
    }
}