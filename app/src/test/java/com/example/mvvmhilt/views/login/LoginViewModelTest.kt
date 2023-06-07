package com.example.mvvmhilt.views.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmhilt.MainCoroutineRule
import com.example.mvvmhilt.common.utils.Validator
import com.example.mvvmhilt.domain.usecase.LoginUseCase
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
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var loginUseCase: LoginUseCase

    @Mock
    lateinit var validator: Validator

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel(loginUseCase, validator)
    }

    @Test
    fun `test valid username and password leads to true event`() {

        val username = "anbarasu"
        val password = "anbarasu90"

        viewModel.validate(username, password)
        Mockito.`when`(validator.validateLoginInput(username, password)).thenReturn(true)
        val result = validator.validateLoginInput(username, password)
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `test invalid username and password leads to false event`() {

        val username = "anba"
        val password = "anbara"

        viewModel.validate(username, password)
        Mockito.`when`(validator.validateLoginInput(username, password)).thenReturn(false)
        val result = validator.validateLoginInput(username, password)
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `test invalid username and password leads to posting failure message in live data`() {

        val username = "anba"
        val password = "anbara"
        val errorMessage =
            "Username should be 6 char, Password should be 6 char and must include 2 digit"

        viewModel.validate(username, password)
        Mockito.`when`(validator.validateLoginInput(username, password)).thenReturn(true)
        validator.validateLoginInput(username, password)
        runBlocking {
            viewModel.errorData.collect { result ->
                Truth.assertThat(result).isEqualTo(errorMessage)
            }
        }

    }

}