package com.example.mvvmhilt.data.repos.auth.datasource

import com.example.mvvmhilt.data.models.AuthInfo
import com.example.mvvmhilt.data.pref.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPrefrenceDataSource @Inject constructor(private val preferenceManager: PreferenceManager) {

    suspend fun login(authInfo: AuthInfo): Boolean =
        preferenceManager.login(authInfo)

    suspend fun logout(): Boolean = preferenceManager.logout()

    suspend fun isLoggedIn(): Boolean = preferenceManager.isLoggedIn()
}