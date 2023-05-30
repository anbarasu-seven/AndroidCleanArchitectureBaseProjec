package com.example.mvvmhilt.views.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmhilt.databinding.LoginActivityBinding
import com.example.mvvmhilt.utils.extn.hideKeyboard
import com.example.mvvmhilt.utils.extn.showToast
import com.example.mvvmhilt.views.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnListeners()
        setObservers()
    }

    private fun setObservers() {
        loginViewModel.errorData.observe(this) {
            showToast(it)
        }
        loginViewModel.navigate.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setOnListeners() {
        binding.login.setOnClickListener {
            hideKeyboard()
            loginViewModel.validate(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }
    }
}