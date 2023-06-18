package com.example.mvvmhilt.domain.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvvmhilt.data.models.AuthInfo

/**
 * Auth data sample
 */

data class AuthInfoDto(
    val name: String,
    val password: String
)

fun AuthInfoDto.toAuthInfo() : AuthInfo = AuthInfo(name, password)
