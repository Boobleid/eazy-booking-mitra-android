package com.booble.reservasi.mitra.ui.service_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.data.model.api.room_facility.RoomFacilityData
import com.booble.reservasi.mitra.databinding.RowItemServiceDetailBinding
import com.polyak.iconswitch.IconSwitch
import com.squareup.picasso.Picasso

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class ServiceDetailAdapter(
    private val context : Context,
    private val detailListener: (RoomFacilityData) -> Unit,
    private val changeListener: (RoomFacilityData) -> Unit,
    private val switchListener: (RoomFacilityData, String) -> Unit,
) : RecyclerView.Adapter<ServiceDetailAdapter.ViewHolder>() {

    private var data = ArrayList<RoomFacilityData>()

    fun setData(item: List<RoomFacilityData>) {
        if (item.isNullOrEmpty()) return
        val oldPos = data.size
        data.addAll(item)
        notifyItemRangeInserted(oldPos, data.size)
    }

    inner class ViewHolder(private val binding: RowItemServiceDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: RoomFacilityData) {
            binding.apply {
                nameTV.text = item.nama
                priceTV.text = item.ket
                Picasso.get()
                    .load(item.foto)
                    .placeholder(R.drawable.placeholder)
                    .into(serviceImageIV)
                detailTV.setOnClickListener { detailListener(item) }
                changeTV.setOnClickListener { changeListener(item) }

                if (item.display == 1) iconSwitch.checked = IconSwitch.Checked.RIGHT
                else iconSwitch.checked = IconSwitch.Checked.LEFT

                iconSwitch.setCheckedChangeListener {
                    if (it == IconSwitch.Checked.RIGHT) {
                        item.display = 1
                        switchListener(item, "1")
                    }
                    else {
                        item.display = 0
                        switchListener(item, "0")
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemServiceDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int = data.size
}