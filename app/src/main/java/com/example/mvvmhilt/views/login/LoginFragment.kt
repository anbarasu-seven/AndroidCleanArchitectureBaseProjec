package com.example.mvvmhilt.views.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mvvmhilt.R
import com.example.mvvmhilt.common.utils.extn.hideKeyboard
import com.example.mvvmhilt.common.utils.extn.showToast
import com.example.mvvmhilt.databinding.LoginFragmentBinding
import com.example.mvvmhilt.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var binding: LoginFragmentBinding? = null

    /**
     * Inflate the layout for this fragment and set [binding]
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    /**
     * Set listeners after view is created/inflated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
    }

    /**
     * listeners for observables
     */
    private fun setObservers() {
        loginViewModel.errorData.observe(viewLifecycleOwner) {
            showToast(it)
        }
        loginViewModel.navigate.observe(viewLifecycleOwner) {
            mainViewModel.navigateTo()
        }
    }

    //user interaction listeners
    private fun setListeners() {
        binding?.login?.setOnClickListener {
            hideKeyboard()
            loginViewModel.validate(
                binding?.username?.text.toString(),
                binding?.password?.text.toString()
            )
        }
    }

}