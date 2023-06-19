package com.example.mvvmhilt.domain.usecase

import com.example.mvvmhilt.data.repos.auth.AuthRepo
import com.example.mvvmhilt.domain.dto.AuthInfoDto
import com.example.mvvmhilt.domain.dto.DataMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(private val authRepo: AuthRepo) {
    suspend fun execute(authInfoDto: AuthInfoDto): Boolean =
        authRepo.login(DataMapper.toAuthInfo(authInfoDto))
}