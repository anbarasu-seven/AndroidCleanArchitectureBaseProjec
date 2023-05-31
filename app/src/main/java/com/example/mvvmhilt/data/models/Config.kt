package com.example.mvvmhilt.data.models

import com.example.mvvmhilt.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

data class Config(
    val BASE_URL: String = BuildConfig.BASE_URL,
    val API_KEY: String = BuildConfig.API_KEY
)