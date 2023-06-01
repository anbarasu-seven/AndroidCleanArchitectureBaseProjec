package com.example.mvvmhilt.domain.repos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmhilt.MainCoroutineRule
import com.example.mvvmhilt.common.utils.Validator
import com.example.mvvmhilt.data.api.room.getOrAwaitValueTest
import com.example.mvvmhilt.data.models.AuthInfo
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.models.User
import com.example.mvvmhilt.data.repos.auth.AuthRepoImpl
import com.example.mvvmhilt.data.repos.auth.datasource.AuthPrefrenceDataSource
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
class AuthRepoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @InjectMocks
    lateinit var authPrefrenceDataSource: AuthPrefrenceDataSource

    private lateinit var authRepoImpl: AuthRepoImpl

    @Before
    fun setup() {
        authRepoImpl = AuthRepoImpl(authPrefrenceDataSource)
    }

    @Test
    fun `test login use case with valid authInfo pojo should return true saying success`() {

        val username = "anbarasu"
        val password = "anbarasu90"
        val authInfo = AuthInfo(username, password)

        runBlocking {
            Mockito.`when`(authPrefrenceDataSource.login(authInfo)).thenReturn(true)
            val result = authRepoImpl.login(authInfo)
            Truth.assertThat(result).isTrue()
        }
    }

    @Test
    fun `test logout return true saying user credentials deleted from preference`() {
        runBlocking {
            Mockito.`when`(authPrefrenceDataSource.logout()).thenReturn(true)
            val result = authRepoImpl.logout()
            Truth.assertThat(result).isTrue()
        }
    }

    @Test
    fun `test is user loggedin into app already - loggedin`() {
        runBlocking {
            Mockito.`when`(authPrefrenceDataSource.isLoggedIn()).thenReturn(true)
            val result = authRepoImpl.isLoggedIn()
            Truth.assertThat(result).isTrue()
        }
    }

    @Test
    fun `test is user loggedin into app already - logged out`() {
        runBlocking {
            Mockito.`when`(authPrefrenceDataSource.isLoggedIn()).thenReturn(false)
            val result = authRepoImpl.isLoggedIn()
            Truth.assertThat(result).isFalse()
        }
    }


}