package com.booble.reservasi.mitra.ui.booking_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.data.model.api.check_history.CheckOutHistoryData
import com.booble.reservasi.mitra.databinding.RowItemCheckOutHistoryBinding
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNullZero
import com.booble.reservasi.mitra.utils.UtilFunctions.toRupiahNotRp

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class BookingHistoryAdapter(
    private val listener: (CheckOutHistoryData) -> Unit
) : ListAdapter<CheckOutHistoryData, BookingHistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: RowItemCheckOutHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: CheckOutHistoryData, position: Int) {
            binding.apply {
                val invoice = root.context.getString(R.string.invoice_, item.invoice)
                val dateTransaction = root.context.getString(R.string.transaction_add, item.tgl)
                val dateBooking = root.context.getString(R.string.booking_date_, item.tglBooking)
                nameTV.text = item.nama
                invoiceTV.text = invoice
                roomNameTV.text = item.nmProduk
                facilityNameTV.text = item.nmProperty
                statusTV.text = item.status
                dateTransactionTV.text = dateTransaction
                dateBookingTV.text = dateBooking
                priceTV.text = toRupiahNotRp(isStringNullZero(item.bayar.toString()))
                root.setOnClickListener { listener(item) }
                if (position % 2 == 0) root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.colorNavyDark))
                else root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.colorNavy))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemCheckOutHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position), position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CheckOutHistoryData>() {
            override fun areItemsTheSame(oldItem: CheckOutHistoryData, newItem: CheckOutHistoryData): Boolean {
                return oldItem.invoice == newItem.invoice
            }

            override fun areContentsTheSame(oldItem: CheckOutHistoryData, newItem: CheckOutHistoryData): Boolean {
                return oldItem == newItem
            }
        }
    }
}