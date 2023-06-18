package com.example.mvvmhilt.data.repos.auth

import com.example.mvvmhilt.data.models.AuthInfo

interface AuthRepo {
    suspend fun login(authInfo: AuthInfo): Boolean
    suspend fun logout(): Boolean
    suspend fun isLoggedIn(): Boolean
}