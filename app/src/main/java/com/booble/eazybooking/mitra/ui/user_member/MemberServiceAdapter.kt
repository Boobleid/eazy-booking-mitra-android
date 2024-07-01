package com.booble.eazybooking.mitra.ui.user_member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.eazybooking.mitra.data.model.api.add_member.MemberServiceData
import com.booble.eazybooking.mitra.databinding.RowItemMemberServiceBinding

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class MemberServiceAdapter(
    private val listenerEdit: (MemberServiceData) -> Unit,
    private val listenerDelete: (MemberServiceData) -> Unit,
) : ListAdapter<MemberServiceData, MemberServiceAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: RowItemMemberServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: MemberServiceData) {
            binding.apply {
                nameTV.text = item.nama
                descTV.text = item.email
                editIV.setOnClickListener { listenerEdit(item) }
                deleteIV.setOnClickListener { listenerDelete(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemMemberServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MemberServiceData>() {
            override fun areItemsTheSame(oldItem: MemberServiceData, newItem: MemberServiceData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MemberServiceData, newItem: MemberServiceData): Boolean {
                return oldItem == newItem
            }
        }
    }
}