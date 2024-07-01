package com.booble.eazybooking.mitra.ui.service_detail.add_room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.eazybooking.mitra.data.model.offline.CheckboxData
import com.booble.eazybooking.mitra.databinding.RowItemCheckBoxBinding
import com.booble.eazybooking.mitra.utils.UtilExtensions.isVisible
import com.booble.eazybooking.mitra.utils.UtilFunctions.formatRupiah
import com.booble.eazybooking.mitra.utils.UtilFunctions.isStringNullZero

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class CheckboxAdapter(
    private val listener: (MutableList<String>) -> Unit
) : ListAdapter<CheckboxData, CheckboxAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var listId = mutableListOf<String>()

    fun setListId(listId: MutableList<String>) {
        this.listId = listId
    }

    inner class ViewHolder(private val binding: RowItemCheckBoxBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: CheckboxData, position: Int) {
            val price = "- ${formatRupiah(isStringNullZero(item.price))}"
            binding.nameCB.text = item.name
            binding.priceTV.text = price
            binding.priceTV.isVisible(item.price != null)
            binding.nameCB.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!listId.contains(item.id)) listId.add(item.id.toString())
                } else if (listId.contains(item.id)) listId.remove(item.id.toString())
                listener(listId)
            }
            if (listId.size > 0) {
                for (i in listId.indices) {
                    if (listId[i] == item.name || listId[i] == item.id) {
                        binding.nameCB.isChecked = true
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemCheckBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position), position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CheckboxData>() {
            override fun areItemsTheSame(oldItem: CheckboxData, newItem: CheckboxData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CheckboxData, newItem: CheckboxData): Boolean {
                return oldItem == newItem
            }
        }
    }
}