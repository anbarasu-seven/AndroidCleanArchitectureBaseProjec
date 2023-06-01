package com.example.mvvmhilt.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Sample data class
 * Ensure each entry is unique
 */

data class AuthInfo(
    val name: String,
    val password: String
)
