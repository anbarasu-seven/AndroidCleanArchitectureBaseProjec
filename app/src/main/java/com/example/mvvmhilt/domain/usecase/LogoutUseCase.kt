package com.example.mvvmhilt.domain.usecase

import com.example.mvvmhilt.data.models.AuthInfo
import com.example.mvvmhilt.domain.repos.AuthRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutUseCase @Inject constructor(private val authRepo: AuthRepo) {
    suspend fun execute(): Boolean = authRepo.logout()
}