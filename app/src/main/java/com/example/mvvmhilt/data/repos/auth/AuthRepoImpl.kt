package com.example.mvvmhilt.data.repos.auth

import com.example.mvvmhilt.data.models.AuthInfo
import com.example.mvvmhilt.data.repos.auth.datasource.AuthPrefrenceDataSource
import com.example.mvvmhilt.domain.repos.AuthRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepoImpl @Inject constructor(
    private val preferenceDataSource: AuthPrefrenceDataSource
) : AuthRepo {

    override suspend fun login(authInfo: AuthInfo): Boolean =
        preferenceDataSource.login(authInfo)

    override suspend fun logout(): Boolean = preferenceDataSource.logout()

    override suspend fun isLoggedIn(): Boolean = preferenceDataSource.isLoggedIn()

}