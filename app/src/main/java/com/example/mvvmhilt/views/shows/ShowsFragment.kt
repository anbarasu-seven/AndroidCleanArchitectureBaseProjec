package com.example.mvvmhilt.views.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anushka.tmdbclient.data.model.tvshow.TvShow
import com.example.mvvmhilt.R
import com.example.mvvmhilt.common.utils.extn.showToast
import com.example.mvvmhilt.databinding.ShowsFragmentBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class ShowsFragment : Fragment(R.layout.shows_fragment) {

    private lateinit var showsAdapter: ShowsAdapter
    private val showsViewModel: ShowsViewModel by activityViewModels()
    private var binding: ShowsFragmentBinding? = null

    /**
     * Inflate the layout for this fragment and set [binding]
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShowsFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    /**
     * Set listeners after view is created/inflated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewAdapterInitially()
        displayPopularTvShows()
    }

    private fun setupRecyclerViewAdapterInitially() {
        showsAdapter = ShowsAdapter(arrayListOf<TvShow>())
        binding?.tvRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = showsAdapter
        }
    }

    private fun displayPopularTvShows() {
        binding?.tvProgressBar?.visibility = View.VISIBLE
        val responseLiveData = showsViewModel.getTvShows()
        responseLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                showsAdapter.apply {
                    setList(it)
                    notifyDataSetChanged()
                }
                binding?.tvProgressBar?.visibility = View.GONE
            } else {
                binding?.tvProgressBar?.visibility = View.GONE
                showToast("No data available")
            }
        })
    }

    //manual update action
    private fun updateTvShows() {
        binding?.tvProgressBar?.visibility = View.VISIBLE
        val response = showsViewModel.updateTvShows()
        response.observe(this, Observer {
            if (it != null) {
                showsAdapter.apply {
                    setList(it)
                    notifyDataSetChanged()
                }
                binding?.tvProgressBar?.visibility = View.GONE
            } else {
                binding?.tvProgressBar?.visibility = View.GONE
            }
        })
    }

}