package com.booble.eazybooking.mitra.ui.help

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.eazybooking.mitra.data.model.api.help.HelpData
import com.booble.eazybooking.mitra.databinding.RowItemHelpBinding

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class HelpAdapter(
    private val listener: (HelpData) -> Unit
) : ListAdapter<HelpData, HelpAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: RowItemHelpBinding, private val listener: (HelpData) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: HelpData) {
            binding.textTV.text = item.pertanyaan
            binding.root.setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemHelpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HelpData>() {
            override fun areItemsTheSame(oldItem: HelpData, newItem: HelpData): Boolean {
                return oldItem.pertanyaan == newItem.pertanyaan
            }

            override fun areContentsTheSame(oldItem: HelpData, newItem: HelpData): Boolean {
                return oldItem == newItem
            }
        }
    }
}