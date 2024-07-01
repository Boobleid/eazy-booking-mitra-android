package com.booble.eazybooking.mitra.ui.help.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.data.model.api.help.contact.ContactData
import com.booble.eazybooking.mitra.databinding.RowItemContactUsBinding
import com.bumptech.glide.Glide

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class ContactAdapter(
    private val listener: (ContactData) -> Unit
) : ListAdapter<ContactData, ContactAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: RowItemContactUsBinding, private val listener: (ContactData) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: ContactData) {
            Glide.with(binding.root.context)
                .load(item.gambar)
                .placeholder(R.drawable.placeholder)
                .error(Glide.with(binding.root.context).load(R.drawable.placeholder))
                .into(binding.imageIV)
            binding.typeTV.text = item.nama
            binding.contactTV.text = item.kontak
            binding.root.setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemContactUsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContactData>() {
            override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
                return oldItem.nama == newItem.nama
            }

            override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
                return oldItem == newItem
            }
        }
    }
}