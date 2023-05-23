package com.example.mvvmhilt.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.mvvmhilt.databinding.ActivityMainBinding
import com.example.mvvmhilt.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToast("ActivitySampleTest")

        binding.testDisplayButton.setOnClickListener {
            changeFragment(SampleFragment())
            Timber.d("Sample Fragment Displayed")
        }
    }

    /**
     * Function to change fragment
     * @param fragment class name
     */
    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.commit {
            add(binding.containerFragment.id,fragment::class.java,null)
        }
    }
}