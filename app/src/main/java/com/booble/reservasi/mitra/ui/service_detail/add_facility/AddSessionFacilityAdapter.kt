package com.booble.reservasi.mitra.ui.service_detail.add_facility

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.data.model.api.service.add_facility.AddFacilitySessionData
import com.booble.reservasi.mitra.databinding.RowItemAddFacilitySessionBinding
import com.booble.reservasi.mitra.utils.UtilExtensions.setTextEditable
import com.booble.reservasi.mitra.utils.UtilFunctions
import com.booble.reservasi.mitra.utils.UtilFunctions.editTextNumberReplace
import com.booble.reservasi.mitra.utils.UtilFunctions.getTimestamp
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNull
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNullZero
import com.booble.reservasi.mitra.utils.UtilFunctions.showTimePickerEnable

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class AddSessionFacilityAdapter(
    private val listenerUpdates: (MutableList<AddFacilitySessionData>) -> Unit,
    private val listenerDeletes: (MutableList<AddFacilitySessionData>) -> Unit,
) : ListAdapter<AddFacilitySessionData, AddSessionFacilityAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val days = mutableListOf<String>()

    inner class ViewHolder(private val binding: RowItemAddFacilitySessionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: AddFacilitySessionData) {
            binding.apply {
                initTimePicker(binding)
                val session = root.context.getString(R.string.session_, (layoutPosition+1).toString())
                hintTV.text = session
                priceET.setTextEditable(isStringNullZero(item.harga))
                quotaET.setTextEditable(isStringNullZero(item.kuota))
                timeStartET.setTextEditable(isStringNullZero(item.jamMulai))
                timeEndET.setTextEditable(isStringNullZero(item.jamAkhir))

                val textWatcherEstimation = UtilFunctions.customWatcherReturn(object : UtilFunctions.ITextWatcher {
                    override fun onTextChanged(charSequence: CharSequence) {
                        updateFacility(binding, item, adapterPosition)
                    }
                })

                priceET.addTextChangedListener(textWatcherEstimation)
                quotaET.addTextChangedListener(textWatcherEstimation)
                timeStartET.addTextChangedListener(textWatcherEstimation)
                timeEndET.addTextChangedListener(textWatcherEstimation)
                clearIV.setOnClickListener {
                    val listSession = currentList.toMutableList()
                    listSession.remove(item)
                    listenerDeletes(listSession)
                }

                checkbox1CB.setOnCheckedChangeListener { _, isChecked ->
                    val text = checkbox1CB.text.toString()
                    if (isChecked) if (!days.contains(text)) days.add(text)
                    if (!isChecked) if (days.contains(text)) days.remove(text)
                    updateFacility(binding, item, adapterPosition)
                }

                checkbox2CB.setOnCheckedChangeListener { _, isChecked ->
                    val text = checkbox2CB.text.toString()
                    if (isChecked) if (!days.contains(text)) days.add(text)
                    if (!isChecked) if (days.contains(text)) days.remove(text)
                    updateFacility(binding, item, adapterPosition)
                }

                checkbox3CB.setOnCheckedChangeListener { _, isChecked ->
                    val text = checkbox3CB.text.toString()
                    if (isChecked) if (!days.contains(text)) days.add(text)
                    if (!isChecked) if (days.contains(text)) days.remove(text)
                    updateFacility(binding, item, adapterPosition)
                }

                checkbox4CB.setOnCheckedChangeListener { _, isChecked ->
                    val text = checkbox4CB.text.toString()
                    if (isChecked) if (!days.contains(text)) days.add(text)
                    if (!isChecked) if (days.contains(text)) days.remove(text)
                    updateFacility(binding, item, adapterPosition)
                }

                checkbox5CB.setOnCheckedChangeListener { _, isChecked ->
                    val text = checkbox5CB.text.toString()
                    if (isChecked) if (!days.contains(text)) days.add(text)
                    if (!isChecked) if (days.contains(text)) days.remove(text)
                    updateFacility(binding, item, adapterPosition)
                }

                checkbox6CB.setOnCheckedChangeListener { _, isChecked ->
                    val text = checkbox6CB.text.toString()
                    if (isChecked) if (!days.contains(text)) days.add(text)
                    if (!isChecked) if (days.contains(text)) days.remove(text)
                    updateFacility(binding, item, adapterPosition)
                }

                checkbox7CB.setOnCheckedChangeListener { _, isChecked ->
                    val text = checkbox7CB.text.toString()
                    if (isChecked) if (!days.contains(text)) days.add(text)
                    if (!isChecked) if (days.contains(text)) days.remove(text)
                    updateFacility(binding, item, adapterPosition)
                }

                if (item.hari != null) {
                    for (check in item.hari ?: return) {
                        checkbox1CB.isChecked = check == checkbox1CB.text.toString()
                        checkbox2CB.isChecked = check == checkbox2CB.text.toString()
                        checkbox3CB.isChecked = check == checkbox3CB.text.toString()
                        checkbox4CB.isChecked = check == checkbox4CB.text.toString()
                        checkbox5CB.isChecked = check == checkbox5CB.text.toString()
                        checkbox6CB.isChecked = check == checkbox6CB.text.toString()
                        checkbox7CB.isChecked = check == checkbox7CB.text.toString()
                    }
                }
            }
        }
    }

    private fun updateFacility(binding: RowItemAddFacilitySessionBinding, item: AddFacilitySessionData, adapterPosition: Int) {
        binding.apply {
            val price = isStringNull(editTextNumberReplace(priceET))
            val quota = isStringNull(quotaET.text.toString())
            val startDate = timeStartET.text.toString()
            val endDate = timeEndET.text.toString()

            val sessionFacility = AddFacilitySessionData(price, days, endDate, startDate, item.sesiKe, quota, getTimestamp())
            val listSession = currentList.toMutableList()
            listSession[adapterPosition] = sessionFacility
            listenerUpdates(listSession)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemAddFacilitySessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    private fun initTimePicker(binding: RowItemAddFacilitySessionBinding) {
        binding.apply {
            timeStartET.setOnClickListener {
                showTimePickerEnable(binding.root.context, object : UtilFunctions.IResultTimePicker{
                    override fun onTimePicker(time: String?) {
                        timeStartET.setTextEditable(time ?: "")
                    }
                })
            }
            timeEndET.setOnClickListener {
                showTimePickerEnable(binding.root.context, object : UtilFunctions.IResultTimePicker{
                    override fun onTimePicker(time: String?) {
                        timeEndET.setTextEditable(time ?: "")
                    }
                })
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AddFacilitySessionData>() {
            override fun areItemsTheSame(oldItem: AddFacilitySessionData, newItem: AddFacilitySessionData): Boolean {
                return oldItem.timeStamp == newItem.timeStamp
            }

            override fun areContentsTheSame(oldItem: AddFacilitySessionData, newItem: AddFacilitySessionData): Boolean {
                return oldItem == newItem
            }
        }
    }
}