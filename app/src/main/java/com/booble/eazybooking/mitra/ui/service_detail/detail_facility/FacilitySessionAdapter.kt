package com.booble.eazybooking.mitra.ui.service_detail.detail_facility

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.data.model.api.service.detail_facility.DetailFacilitySessionData
import com.booble.eazybooking.mitra.databinding.RowItemSessionFacilityBinding
import com.booble.eazybooking.mitra.utils.UtilFunctions.formatRupiah
import com.booble.eazybooking.mitra.utils.UtilFunctions.isStringNullZero

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class FacilitySessionAdapter : ListAdapter<DetailFacilitySessionData, FacilitySessionAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: RowItemSessionFacilityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: DetailFacilitySessionData) {
            binding.apply {
                val time = "${item.jamMulai} - ${item.jamAkhir}"
                hintTV.text = root.context.getString(R.string.session_, item.sesiKe.toString())
                priceTV.text = formatRupiah(isStringNullZero(item.harga.toString()))
                operationalHourTV.text = time
                operationalDayTV.text = item.hari?.joinToString() ?: root.context.getString(R.string.dash)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemSessionFacilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailFacilitySessionData>() {
            override fun areItemsTheSame(oldItem: DetailFacilitySessionData, newItem: DetailFacilitySessionData): Boolean {
                return oldItem.sesiKe == newItem.sesiKe
            }

            override fun areContentsTheSame(oldItem: DetailFacilitySessionData, newItem: DetailFacilitySessionData): Boolean {
                return oldItem == newItem
            }
        }
    }
}