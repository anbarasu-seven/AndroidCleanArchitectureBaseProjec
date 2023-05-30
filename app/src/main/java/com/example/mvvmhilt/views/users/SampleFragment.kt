package com.example.mvvmhilt.views.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmhilt.R
import com.example.mvvmhilt.data.models.Resource
import com.example.mvvmhilt.data.models.UserData
import com.example.mvvmhilt.databinding.FragmentSampleBinding
import com.example.mvvmhilt.utils.extn.showToast

/**
 * A simple [Fragment] subclass.
 */
class SampleFragment : Fragment(R.layout.fragment_sample) {

    private lateinit var recycleAdapter: SampleAdapter
    private val sampleViewModel: SampleViewModel by activityViewModels()
    private var binding: FragmentSampleBinding? = null

    /**
     * Inflate the layout for this fragment and set [binding]
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSampleBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    /**
     * Set listeners after view is created/inflated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToast("FragmentSampleTest")
        addClickListeners()
        setupRecyclerViewAdapterInitially()
        observerData()
    }

    private fun setupRecyclerViewAdapterInitially() {
        binding?.sampleRecyclerList?.apply {
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)
            recycleAdapter = SampleAdapter()
            adapter = recycleAdapter
            recycleAdapter.submitList(arrayListOf<UserData>())
        }
    }

    private fun addClickListeners(){
        binding?.testGetButton?.setOnClickListener {
            sampleViewModel.performNetworkRequest()
        }

        binding?.testDelButton?.setOnClickListener {
            sampleViewModel.deleteData()
            showToast("Data deleted")
        }
    }

    private fun observerData() {

        //Set observer for webData
        sampleViewModel.apiUsersData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    sampleViewModel.insertData(it.data?.people!!)
                }
                is Resource.Error -> {
                    showToast(it.message!!)
                }
                else -> {}
            }
        }

        /**
         * listerner to observer network data from room db
         * Display data in recycle view
         */
        sampleViewModel.roomUsersData.observe(viewLifecycleOwner) {
            //this work as notifyDatasetChanged()
            recycleAdapter.submitList(it)
        }
    }

}