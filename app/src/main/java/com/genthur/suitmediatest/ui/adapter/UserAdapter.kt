package com.genthur.suitmediatest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.genthur.suitmediatest.data.remote.response.DataItem
import com.genthur.suitmediatest.databinding.ItemListBinding

class UserAdapter: PagingDataAdapter<DataItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MyViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(users: DataItem) {
            Glide.with(binding.imgUser.context)
                .load(users.avatar)
                .into(binding.imgUser)
            binding.tvFirstName.text = users.firstName
            binding.tvLastname.text = users.lastName
            binding.tvEmail.text = users.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.bind(it) }

        holder.itemView.setOnClickListener {
            user?.let { onItemClickCallback.onItemClicked(it) }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataItem)
    }
}