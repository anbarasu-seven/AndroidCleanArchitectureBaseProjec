package com.example.mvvmhilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmhilt.R
import com.example.mvvmhilt.databinding.SampleItemBinding
import com.example.mvvmhilt.models.NetworkData
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class SampleAdapter : ListAdapter<NetworkData, SampleAdapter.ViewHolder>(NetworkDataCallBack()) {

    class ViewHolder(val binding: SampleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(nameData: String?, craftData: String?) {
            binding.nameTextView.text = nameData
            binding.craftTextView.text = craftData
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val itemBinding = SampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(itemBinding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position).name, getItem(position).craft)
    }
}

class NetworkDataCallBack : DiffUtil.ItemCallback<NetworkData>() {

    override fun areItemsTheSame(oldItem: NetworkData, newItem: NetworkData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NetworkData, newItem: NetworkData): Boolean {
        return oldItem.name == newItem.name
    }
}
