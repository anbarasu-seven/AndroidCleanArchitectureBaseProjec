package com.example.mvvmhilt.domain.usecase

import com.example.mvvmhilt.domain.repos.AuthRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyLoginUseCase @Inject constructor(private val authRepo: AuthRepo) {
    suspend fun execute(): Boolean = authRepo.isLoggedIn()
}