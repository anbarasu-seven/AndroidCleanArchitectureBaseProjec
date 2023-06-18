package com.example.mvvmhilt.domain.usecase

import com.example.mvvmhilt.data.repos.auth.AuthRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutUseCase @Inject constructor(private val authRepo: AuthRepo) {
    suspend fun execute(): Boolean = authRepo.logout()
}