package com.example.mvvmhilt.presenter.shows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmhilt.common.utils.extn.loadFromUrl
import com.example.mvvmhilt.databinding.ListItemBinding
import com.example.mvvmhilt.domain.dto.TvShowDto
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class ShowsAdapter(val tvList: ArrayList<TvShowDto>) :
    RecyclerView.Adapter<ShowsAdapter.ShowsHolder>() {

    fun setList(tvShows: List<TvShowDto>?) {
        if (tvShows != null) {
            tvList.clear()
            tvList.addAll(tvShows)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        return ShowsHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return tvList.size
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {
        holder.bind(tvList[position])
    }

    class ShowsHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShow: TvShowDto) {
            binding.titleTextView.text = tvShow.name
            binding.descriptionTextView.text = tvShow.overview
            val posterURL = "https://image.tmdb.org/t/p/w500" + tvShow.posterPath
            binding.imageView.loadFromUrl(posterURL)
        }

        companion object {
            fun create(parent: ViewGroup): ShowsHolder {
                val binding =
                    ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ShowsHolder(binding)
            }
        }

    }
}


