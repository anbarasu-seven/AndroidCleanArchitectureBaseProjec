package com.example.mvvmhilt.presenter.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmhilt.R
import com.example.mvvmhilt.common.utils.extn.hide
import com.example.mvvmhilt.common.utils.extn.show
import com.example.mvvmhilt.common.utils.extn.showToast
import com.example.mvvmhilt.databinding.ShowsFragmentBinding
import com.example.mvvmhilt.domain.dto.UIState
import com.example.mvvmhilt.presenter.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class ShowsFragment : Fragment(R.layout.shows_fragment) {

    private lateinit var showsAdapter: ShowsAdapter
    private lateinit var binding: ShowsFragmentBinding
    private val showsViewModel: ShowsViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    /**
     * Inflate the layout for this fragment and set [binding]
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShowsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Set listeners after view is created/inflated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.chageTitle(getString(R.string.shows))
        setupRecyclerViewAdapterInitially()
        displayPopularTvShows()
        showsViewModel.getTvShows()
    }

    private fun setupRecyclerViewAdapterInitially() {
        showsAdapter = ShowsAdapter(arrayListOf())
        binding.tvRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = showsAdapter
        }
    }

    private fun displayPopularTvShows() {
        showsViewModel.tvShows.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is UIState.Loading -> {
                        //show progress bar
                        binding.tvProgressBar.show()
                    }
                    is UIState.Success -> {
                        it.data?.let { result ->
                            showsAdapter.apply {
                                setList(result.tvShows)
                                notifyDataSetChanged()
                            }
                        }
                        binding.tvProgressBar.hide()
                    }
                    is UIState.Error -> {
                        binding.tvProgressBar.hide()
                        it.message?.let { it1 -> showToast(it1) }
                    }
                }
            }
        }
    }

}