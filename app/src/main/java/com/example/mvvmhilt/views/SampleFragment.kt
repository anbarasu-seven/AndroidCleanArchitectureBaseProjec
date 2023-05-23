package com.example.mvvmhilt.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmhilt.R
import com.example.mvvmhilt.adapter.SampleAdapter
import com.example.mvvmhilt.databinding.FragmentSampleBinding
import com.example.mvvmhilt.extensions.showToast
import com.example.mvvmhilt.models.NetworkData
import com.example.mvvmhilt.viewmodels.SampleViewModel
import timber.log.Timber

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
        setupRecyclerViewAdapterInitially()
        observerData()


    }

    private fun setupRecyclerViewAdapterInitially() {
        binding?.sampleRecyclerList?.apply {
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)
            recycleAdapter = SampleAdapter()
            adapter = recycleAdapter
            recycleAdapter.submitList(arrayListOf<NetworkData>())
        }
    }

    private fun observerData() {
        //Set observer for webData
        sampleViewModel.webData.observe(viewLifecycleOwner) {
            Timber.d("It is:%s", it)
            it.forEach { data ->
                sampleViewModel.insertData(data)
            }
        }

        binding?.testGetButton?.setOnClickListener {
            sampleViewModel.performNetworkRequest()
        }

        binding?.testDelButton?.setOnClickListener {
            sampleViewModel.deleteData()
            showToast("Data deleted")
        }


        /**
         * listerner to observer network data from room db
         * Display data in recycle view
         */
        sampleViewModel.networkData.observe(viewLifecycleOwner) {
            recycleAdapter.submitList(it)
        }
    }

}