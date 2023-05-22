package com.booble.reservasi.mitra.ui.booking_history.calendar_booking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.data.model.offline.CalendarModel
import com.booble.reservasi.mitra.databinding.RowItemDateBinding
import java.util.*

class CalendarAdapter(
    private val mData: ArrayList<CalendarModel>,
    private val selectedDate: (String) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    var selectedItemPosition = RecyclerView.NO_POSITION

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val item = mData[position]
        holder.bind(item)
    }

    private fun dateBuilder(tanggal: Int, bulan: Int, tahun: Int): String {
        val tgl: String = if (tanggal.toString().length == 1) {
            "0$tanggal"
        } else {
            "" + tanggal
        }
        val bln: String = if (bulan.toString().length == 1) {
            "0$bulan"
        } else {
            "" + bulan
        }
        return "$tahun-$bln-$tgl"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = RowItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class CalendarViewHolder(private val binding: RowItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CalendarModel) {
            binding.date.text = item.date.toString()

            itemView.setOnClickListener {
                val date = dateBuilder(item.date, item.month + 1, item.year)
                selectedDate(date)
                
                selectedItemPosition = adapterPosition
                notifyDataSetChanged()
            }

            if (item.month == item.calendarCompare.get(Calendar.MONTH) && item.year == item.calendarCompare.get(Calendar.YEAR)) {
                binding.date.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorWhiteDark))
            } else {
                binding.date.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorGreyDarkHigh))
            }

            if (item.isBooking == true) {
                binding.dot.visibility = View.VISIBLE
            } else binding.dot.visibility = View.GONE

            if (adapterPosition == selectedItemPosition) {
                binding.container.backgroundTintList =
                    ContextCompat.getColorStateList(itemView.context, R.color.colorAccent)
            } else {
                binding.container.backgroundTintList =
                    ContextCompat.getColorStateList(itemView.context, R.color.transparant)
            }
        }
    }
}