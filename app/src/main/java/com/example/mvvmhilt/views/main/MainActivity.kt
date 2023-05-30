package com.example.mvvmhilt.views.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.mvvmhilt.R
import com.example.mvvmhilt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.testDisplayButton.setOnClickListener {
            changeFragment()
        }
    }

    /**
     * Function to change fragment
     * @param fragment class name
     */
    private fun changeFragment(){
        findNavController(R.id.container).navigate(R.id.searchFragment)
        /*supportFragmentManager.commit {
            add(binding.containerFragment.id,fragment::class.java,null)
        }*/
    }
}