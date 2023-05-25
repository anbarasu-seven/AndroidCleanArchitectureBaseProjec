package com.example.mvvmhilt.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Sample data class
 * Ensure each entry is unique
 */
@Entity(tableName = "sample_table")
data class UserData(
    @PrimaryKey
    val name: String,
    val craft: String
)

data class UserResponse(val message: String, val number: Int, val people: ArrayList<UserData>)

data class ErrorResponse(val status: String, val failureMessage: String?)
