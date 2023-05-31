package com.example.mvvmhilt.views.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmhilt.data.models.User
import com.example.mvvmhilt.databinding.SampleItemBinding
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class UsersAdapter : ListAdapter<User, UsersAdapter.ViewHolder>(NetworkDataCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position).name, getItem(position).craft)
    }

    class ViewHolder(val binding: SampleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(nameData: String?, craftData: String?) {
            binding.nameTextView.text = nameData
            binding.craftTextView.text = craftData
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val itemBinding =
                    SampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(itemBinding)
            }
        }
    }
}

class NetworkDataCallBack : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.name == newItem.name
    }
}
