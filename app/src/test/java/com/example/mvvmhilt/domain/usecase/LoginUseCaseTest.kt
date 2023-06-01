package com.example.mvvmhilt.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmhilt.MainCoroutineRule
import com.example.mvvmhilt.common.utils.Validator
import com.example.mvvmhilt.data.api.room.getOrAwaitValueTest
import com.example.mvvmhilt.data.models.AuthInfo
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.models.User
import com.example.mvvmhilt.domain.repos.AuthRepo
import com.example.mvvmhilt.domain.usecase.LoginUseCase
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.validateMockitoUsage
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var authRepo: AuthRepo

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        loginUseCase = LoginUseCase(authRepo)
    }

    @Test
    fun `test login use case with valid authInfo pojo should return true saying success`() {

        val username = "anbarasu"
        val password = "anbarasu90"
        val authInfo = AuthInfo(username, password)

        runBlocking {
            Mockito.`when`(authRepo.login(authInfo)).thenReturn(true)
            val result = loginUseCase.execute(authInfo)
            Truth.assertThat(result).isTrue()
        }
    }

    @Test
    fun `test login use case with invalid authInfo pojo should return false saying failure`() {

        val username = "anba"
        val password = "anbar"
        val authInfo = AuthInfo(username, password)

        runBlocking {
            Mockito.`when`(authRepo.login(authInfo)).thenReturn(false)
            val result = loginUseCase.execute(authInfo)
            Truth.assertThat(result).isFalse()
        }
    }


}