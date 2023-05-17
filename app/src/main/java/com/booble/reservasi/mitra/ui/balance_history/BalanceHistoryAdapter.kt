package com.booble.reservasi.mitra.ui.balance_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.data.model.api.balance_history.BalanceHistoryData
import com.booble.reservasi.mitra.databinding.RowItemBalanceHistoryBinding
import com.booble.reservasi.mitra.utils.UtilConstants.STR_OUT
import com.booble.reservasi.mitra.utils.UtilConstants.STR_PAID
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilFunctions.formatRupiah
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNullZero

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class BalanceHistoryAdapter(
    private val listener: (BalanceHistoryData) -> Unit
) : ListAdapter<BalanceHistoryData, BalanceHistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: RowItemBalanceHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: BalanceHistoryData, position: Int) {
            binding.apply {
                val date1 = root.context.getString(R.string.transaction_add, item.tanggal)
                val invoice = root.context.getString(R.string.invoice_, item.invoice)
                var status = root.context.getString(R.string.on_process)
                if (item.status == STR_PAID) {
                    statusTV.isVisible(item.keterangan == STR_OUT)
                    statusTV.setBackgroundResource(R.drawable.bg_blue_r50)
                    status = "Sukses"
                }
                nameTV.text = item.keterangan
                invoiceTV.text = invoice
                statusTV.text = status
                dateStatusTV.text = date1
                priceTV.text = formatRupiah(isStringNullZero(item.jumlahBonus.toString()))
                root.setOnClickListener { listener(item) }
                if (position % 2 == 0) root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.colorNavyDark))
                else root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.colorNavy))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemBalanceHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position), position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BalanceHistoryData>() {
            override fun areItemsTheSame(oldItem: BalanceHistoryData, newItem: BalanceHistoryData): Boolean {
                return oldItem.invoice == newItem.invoice
            }

            override fun areContentsTheSame(oldItem: BalanceHistoryData, newItem: BalanceHistoryData): Boolean {
                return oldItem == newItem
            }
        }
    }
}