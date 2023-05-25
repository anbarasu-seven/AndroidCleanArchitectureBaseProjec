package com.example.mvvmhilt.views.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mvvmhilt.R
import com.example.mvvmhilt.databinding.DummyFragmentBinding

/**
 * A simple [Fragment] subclass.
*/
class DummyFragment : Fragment(R.layout.dummy_fragment) {

    private var binding: DummyFragmentBinding? = null

    /**
     * Inflate the layout for this fragment and set [binding]
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DummyFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    /**
     * Set listeners after view is created/inflated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //displayData()
    }

    /**
     * Function to display network data by observing changes from room db
     * Display data in recycle view
     */
    private fun displayData(){

    }

}