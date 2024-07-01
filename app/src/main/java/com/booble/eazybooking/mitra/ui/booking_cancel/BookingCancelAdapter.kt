package com.booble.eazybooking.mitra.ui.booking_cancel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.data.model.api.booking_cancel.BookingCancelItem
import com.booble.eazybooking.mitra.databinding.RowItemBookingCancelBinding
import com.booble.eazybooking.mitra.utils.UtilFunctions.isStringNullZero
import com.booble.eazybooking.mitra.utils.UtilFunctions.toRupiahNotRp

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class BookingCancelAdapter(
    private val listener: (BookingCancelItem) -> Unit
) : ListAdapter<BookingCancelItem, BookingCancelAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: RowItemBookingCancelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItem(item: BookingCancelItem, position: Int) {
            binding.apply {
                val invoice = root.context.getString(R.string.invoice_, item.invoice)
                val requestDate = "Pengajuan ${item.tglCreateBatal}"
                val confirmDate = "Selesai ${item.tglConfirmBatal}"
                nameTV.text = item.nama
                invoiceTV.text = invoice
                locationNameTV.text = item.nmProduk
                facilityNameTV.text = item.nmProperty
                statusTV.text = item.statusBatal
                requestDateTV.text = requestDate
                confirmDateTV.text = confirmDate
                priceTV.text = toRupiahNotRp(isStringNullZero(item.bayar.toString()))

                root.setOnClickListener { listener(item) }

                when (item.statusBatal) {
                    "Pengajuan" -> {
                        binding.statusTV.backgroundTintList =
                            ContextCompat.getColorStateList(itemView.context, R.color.colorAccent)
                    }
                    "Ditolak" -> {
                        binding.statusTV.backgroundTintList =
                            ContextCompat.getColorStateList(itemView.context, R.color.colorRed)
                    }
                    else -> {
                        binding.statusTV.backgroundTintList =
                            ContextCompat.getColorStateList(itemView.context, R.color.colorGreen)
                    }
                }

                if (item.tglConfirmBatal?.isNotEmpty() == true) {
                    binding.confirmDateTV.visibility = View.VISIBLE
                } else binding.confirmDateTV.visibility = View.GONE

                if ((item.pesanBelumDibaca ?: 0) > 0) {
                    binding.newMessageTV.text = "${item.pesanBelumDibaca} Pesan Baru"
                    binding.newMessageTV.visibility = View.VISIBLE
                } else binding.newMessageTV.visibility = View.GONE

                if (position % 2 == 0) root.setBackgroundColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.colorNavyDark
                    )
                )
                else root.setBackgroundColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.colorNavy
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowItemBookingCancelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position), position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookingCancelItem>() {
            override fun areItemsTheSame(
                oldItem: BookingCancelItem,
                newItem: BookingCancelItem
            ): Boolean {
                return oldItem.invoice == newItem.invoice
            }

            override fun areContentsTheSame(
                oldItem: BookingCancelItem,
                newItem: BookingCancelItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}