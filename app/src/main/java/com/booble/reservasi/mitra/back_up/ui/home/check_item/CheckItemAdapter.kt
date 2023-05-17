package com.booble.reservasi.mitra.back_up.ui.home.check_item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_item.BookingItemData
import com.booble.reservasi.mitra.databinding.RowItemItemBinding
import com.booble.reservasi.mitra.utils.UtilFunctions

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class CheckItemAdapter(
    private val listener: (BookingItemData) -> Unit
) : ListAdapter<BookingItemData, CheckItemAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: RowItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: BookingItemData) {
            binding.apply {
                val status = if (UtilFunctions.isStringNull(item.status).isNotEmpty()) item.status else root.context?.getString(R.string.good)
                statusTV.text = status
                itemTV.text = item.nmBarang
                editMB.setOnClickListener { listener(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookingItemData>() {
            override fun areItemsTheSame(oldItem: BookingItemData, newItem: BookingItemData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BookingItemData, newItem: BookingItemData): Boolean {
                return oldItem == newItem
            }
        }
    }
}