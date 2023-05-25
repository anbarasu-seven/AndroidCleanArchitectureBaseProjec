package com.example.mvvmhilt.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.mvvmhilt.R
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResValidatorTest {

    private lateinit var resValidator: ResValidator

    @Before
    fun setUp() {
        resValidator = ResValidator()
    }

    @After
    fun cleanUp(){}

    @Test
    fun validateApplicationName_fromResourceFile() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resValidator.isEqual(context = context, R.string.app_name, "MVVM Hilt")
        assertThat(result).isTrue()
    }
}