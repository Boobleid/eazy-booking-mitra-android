package com.booble.eazybooking.mitra.ui.booking_history.detail

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.data.model.api.check_history.SessionBooking
import com.booble.eazybooking.mitra.databinding.RowItemSessionInfoBinding
import com.booble.eazybooking.mitra.utils.UtilFunctions
import com.booble.eazybooking.mitra.utils.UtilFunctions.formatRupiah

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class SessionBookingAdapter :
    ListAdapter<SessionBooking, SessionBookingAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: RowItemSessionInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItem(item: SessionBooking) {
            binding.apply {
                val time = item.jam
                val session = itemView.context.getString(R.string.session_text_, item.sesiKe)
                var price = item.harga ?: 0
                val discount = item.disc ?: 0

                if (discount > 0) {
                    price -= discount
                    binding.priceTV.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    binding.discountPriceTV.visibility = View.VISIBLE
                    binding.discountPriceTV.text =
                        formatRupiah(UtilFunctions.isStringNullZero(price.toString()))
                } else {
                    binding.priceTV.paintFlags = Paint.ANTI_ALIAS_FLAG
                    binding.discountPriceTV.visibility = View.GONE
                }

                binding.hintSessionTV.text = "$session ($time)"
                binding.priceTV.text = formatRupiah(item.harga.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowItemSessionInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SessionBooking>() {
            override fun areItemsTheSame(
                oldItem: SessionBooking,
                newItem: SessionBooking
            ): Boolean {
                return oldItem.sesiKe == newItem.sesiKe
            }

            override fun areContentsTheSame(
                oldItem: SessionBooking,
                newItem: SessionBooking
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}