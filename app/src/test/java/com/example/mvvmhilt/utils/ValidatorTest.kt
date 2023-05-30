package com.example.mvvmhilt.utils

import com.example.mvvmhilt.common.utils.Validator
import com.google.common.truth.Truth.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class ValidatorTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `empty username should return false`(){
        val result = Validator.validateLoginInput(username = "", password = "steven12")
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password should return false`(){
        val result = Validator.validateLoginInput(username = "username", password = "")
        assertThat(result).isFalse()
    }

    @Test
    fun `username with lesser than six charactor returns false`(){
        val result = Validator.validateLoginInput(username = "userna", password = "")
        assertThat(result).isFalse()
    }

    @Test
    fun `password with lesser than six charactor and not contain 2 digit returns false`(){
        val result = Validator.validateLoginInput(username = "username", password = "pasword1")
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and password should return true`(){
        val result = Validator.validateLoginInput(username = "username", password = "password12")
        assertThat(result).isTrue()
    }
}