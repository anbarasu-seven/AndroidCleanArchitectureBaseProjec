package com.example.mvvmhilt.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Auth data sample
 */

data class AuthInfo(
    val name: String,
    val password: String
)
