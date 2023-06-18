package com.example.mvvmhilt.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.mvvmhilt.R
import com.example.mvvmhilt.ui.support.Route
import com.example.mvvmhilt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setObservers()
        mainViewModel.verifyLoggin()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_custom, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId: Int = item.itemId
        if (itemId == R.id.logout) {
            mainViewModel.logout()
            return false
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val myMenuItem = menu.findItem(R.id.logout)
        mainViewModel.isLoggedIn.value?.let {
            myMenuItem.isVisible = it
        }
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * listeners for observables
     */
    private fun setObservers() {
        mainViewModel.navigate.observe(this) {
            if (it == Route.LOGIN) {
                findNavController(R.id.container).navigate(
                    R.id.action_showsFragment_to_loginFragment
                )
            } else if (it == Route.SHOWS) {
                findNavController(R.id.container).navigate(
                    R.id.action_loginFragment_to_showsFragment
                )
            }
            mainViewModel.verifyLoggin()
        }

        mainViewModel.pageTitle.observe(this) {
            binding.toolbarTitle.text = it
        }

        mainViewModel.isLoggedIn.observe(this) {
            invalidateOptionsMenu()
        }
    }

}