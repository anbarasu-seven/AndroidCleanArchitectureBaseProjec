package com.example.mvvmhilt.common

import com.example.mvvmhilt.common.utils.Validator
import com.example.mvvmhilt.domain.usecase.LoginUseCase
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

class ValidatorTest {

    private var validator: Validator = Validator()

    @Test
    fun `empty username should return false`(){
        val result = validator.validateLoginInput(username = "", password = "steven12")
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `empty password should return false`(){
        val result = validator.validateLoginInput(username = "username", password = "")
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `username with lesser than six charactor returns false`(){
        val result = validator.validateLoginInput(username = "userna", password = "")
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `password with lesser than six charactor and not contain 2 digit returns false`(){
        val result = validator.validateLoginInput(username = "username", password = "pasword1")
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid username and password should return true`(){
        val result = validator.validateLoginInput(username = "username", password = "password12")
        Truth.assertThat(result).isTrue()
    }
}