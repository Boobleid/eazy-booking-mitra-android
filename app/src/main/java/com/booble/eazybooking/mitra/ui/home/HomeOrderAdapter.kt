package com.booble.eazybooking.mitra.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.data.model.api.service.ServiceData
import com.booble.eazybooking.mitra.databinding.RowItemYourServiceBinding
import com.squareup.picasso.Picasso

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class HomeOrderAdapter(
    private val listener: (ServiceData) -> Unit,
    private val listenerEdit: (ServiceData) -> Unit,
) : ListAdapter<ServiceData, HomeOrderAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: RowItemYourServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: ServiceData) {
            binding.apply {
                nameTV.text = item.nama
                root.setOnClickListener { listener(item) }
                editIV.setOnClickListener { listenerEdit(item) }
                Picasso.get() //only for using cache
                    .load(item.foto)
                    .placeholder(R.drawable.placeholder)
                    .into(serviceImageIV)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemYourServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ServiceData>() {
            override fun areItemsTheSame(oldItem: ServiceData, newItem: ServiceData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ServiceData, newItem: ServiceData): Boolean {
                return oldItem == newItem
            }
        }
    }
}