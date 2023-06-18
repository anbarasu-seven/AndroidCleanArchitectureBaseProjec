package com.example.mvvmhilt.data.repos.auth.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmhilt.MainCoroutineRule
import com.example.mvvmhilt.data.models.AuthInfo
import com.example.mvvmhilt.data.setups.pref.PreferenceManager
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthPreferenceDataStoreTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var preferenceManager: PreferenceManager

    private lateinit var authPrefrenceDataSource: AuthLocalDataSource

    @Before
    fun setup() {
        authPrefrenceDataSource = AuthLocalDataSource(preferenceManager)
    }

    @Test
    fun `test login use case with valid authInfo pojo should return true saying success`() {

        val username = "anbarasu"
        val password = "anbarasu90"
        val authInfo = AuthInfo(username, password)

        runBlocking {
            Mockito.`when`(preferenceManager.login(authInfo)).thenReturn(true)
            val result = authPrefrenceDataSource.login(authInfo)
            Truth.assertThat(result).isTrue()
        }
    }

    @Test
    fun `test logout return true saying user credentials deleted from preference`() {
        runBlocking {
            Mockito.`when`(preferenceManager.logout()).thenReturn(true)
            val result = authPrefrenceDataSource.logout()
            Truth.assertThat(result).isTrue()
        }
    }

    @Test
    fun `test is user loggedin into app already - loggedin`() {
        runBlocking {
            Mockito.`when`(preferenceManager.isLoggedIn()).thenReturn(true)
            val result = authPrefrenceDataSource.isLoggedIn()
            Truth.assertThat(result).isTrue()
        }
    }

    @Test
    fun `test is user loggedin into app already - logged out`() {
        runBlocking {
            Mockito.`when`(preferenceManager.isLoggedIn()).thenReturn(false)
            val result = authPrefrenceDataSource.isLoggedIn()
            Truth.assertThat(result).isFalse()
        }
    }


}