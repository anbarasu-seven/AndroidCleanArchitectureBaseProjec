package com.example.mvvmhilt.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mvvmhilt.R
import com.example.mvvmhilt.common.utils.extn.hideKeyboard
import com.example.mvvmhilt.common.utils.extn.showSnackbar
import com.example.mvvmhilt.databinding.LoginFragmentBinding
import com.example.mvvmhilt.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: LoginFragmentBinding

    /**
     * Inflate the layout for this fragment and set [binding]
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Set listeners after view is created/inflated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.chageTitle(getString(R.string.login))
        setObservers()
        setListeners()
    }

    /**
     * listeners for observables
     */
    private fun setObservers() {
        loginViewModel.errorData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.run {
                showSnackbar(this)
            }
        }
        loginViewModel.navigate.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.run {
              mainViewModel.navigateTo(this)
            }
        }
    }

    //user interaction listeners
    private fun setListeners() {
        binding.login.setOnClickListener {
            hideKeyboard()
            loginViewModel.validate(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }
    }

}