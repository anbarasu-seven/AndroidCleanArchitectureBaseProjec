package com.example.mvvmhilt.data.models

import com.example.mvvmhilt.BuildConfig

data class Config(
    val BASE_URL: String = BuildConfig.BASE_URL,
    val PATH: String = BuildConfig.PATH,
    val API_KEY: String = BuildConfig.API_KEY
)