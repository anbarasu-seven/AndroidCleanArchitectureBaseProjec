package com.example.mvvmhilt.domain.usecase

import com.example.mvvmhilt.domain.dto.AuthInfoDto
import com.example.mvvmhilt.domain.dto.toAuthInfo
import com.example.mvvmhilt.data.repos.auth.AuthRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(private val authRepo: AuthRepo) {
    suspend fun execute(authInfo: AuthInfoDto): Boolean = authRepo.login(authInfo.toAuthInfo())
}