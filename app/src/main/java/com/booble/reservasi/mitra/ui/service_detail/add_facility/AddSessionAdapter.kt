package com.booble.reservasi.mitra.ui.service_detail.add_facility

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.data.model.api.service.add_facility.AddFacilitySessionData
import com.booble.reservasi.mitra.databinding.RowItemAddFacilitySessionBinding
import com.booble.reservasi.mitra.utils.UtilExtensions.setTextEditable
import com.booble.reservasi.mitra.utils.UtilFunctions
import com.booble.reservasi.mitra.utils.UtilFunctions.loge

/**
 * Created by rivaldy on 21/09/21
 * Find me on my Github -> https://github.com/im-o
 **/

@SuppressLint("NotifyDataSetChanged")
class AddSessionAdapter(
    private val listenerUpdates: (MutableList<AddFacilitySessionData>) -> Unit,
    private val listenerDeletes: (MutableList<AddFacilitySessionData>) -> Unit,
) : RecyclerView.Adapter<AddSessionAdapter.AddSessionViewHolder>() {
    private var listData = mutableListOf<AddFacilitySessionData>()
    private val days = mutableListOf<String>()

    fun setAddSessions(listData: MutableList<AddFacilitySessionData>) {
        this.listData.clear()
        this.listData.addAll(listData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSessionViewHolder {
        val binding = RowItemAddFacilitySessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddSessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddSessionViewHolder, position: Int) {
        val item = listData[position]
        holder.bindItem(item)
    }

    override fun getItemCount() = listData.size

    inner class AddSessionViewHolder(private val binding: RowItemAddFacilitySessionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: AddFacilitySessionData) {
            binding.apply {
                initTimePicker(binding)
                val session = root.context.getString(R.string.session_, (layoutPosition + 1).toString())
                hintTV.text = session
                priceET.setTextEditable(UtilFunctions.isStringNullZero(item.harga))
                quotaET.setTextEditable(UtilFunctions.isStringNullZero(item.kuota))
                timeStartET.setTextEditable(UtilFunctions.isStringNullZero(item.jamMulai))
                timeEndET.setTextEditable(UtilFunctions.isStringNullZero(item.jamAkhir))

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
                    val listSession = listData.toMutableList()
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
                    if (item.hari?.size ?: 0 > 0) {
                        for (check in item.hari!!) {
                            if (check == checkbox1CB.text.toString()) checkbox1CB.isChecked = true
                            if (check == checkbox2CB.text.toString()) checkbox2CB.isChecked = true
                            if (check == checkbox3CB.text.toString()) checkbox3CB.isChecked = true
                            if (check == checkbox4CB.text.toString()) checkbox4CB.isChecked = true
                            if (check == checkbox5CB.text.toString()) checkbox5CB.isChecked = true
                            if (check == checkbox6CB.text.toString()) checkbox6CB.isChecked = true
                            if (check == checkbox7CB.text.toString()) checkbox7CB.isChecked = true
                        }
                    } else {
                        checkbox1CB.isChecked = false
                        checkbox2CB.isChecked = false
                        checkbox3CB.isChecked = false
                        checkbox4CB.isChecked = false
                        checkbox5CB.isChecked = false
                        checkbox6CB.isChecked = false
                        checkbox7CB.isChecked = false
                    }
                }
            }
        }
    }

    private fun updateFacility(binding: RowItemAddFacilitySessionBinding, item: AddFacilitySessionData, adapterPosition: Int) {
        binding.apply {
            val price = UtilFunctions.isStringNull(UtilFunctions.editTextNumberReplace(priceET))
            val quota = UtilFunctions.isStringNull(quotaET.text.toString())
            val startDate = timeStartET.text.toString()
            val endDate = timeEndET.text.toString()

            val sessionFacility = AddFacilitySessionData(price, days, endDate, startDate, item.sesiKe, quota, UtilFunctions.getTimestamp())
            val listSession = listData.toMutableList()
            listSession[adapterPosition] = sessionFacility
            listenerUpdates(listSession)
        }
    }

    private fun initTimePicker(binding: RowItemAddFacilitySessionBinding) {
        binding.apply {
            timeStartET.setOnClickListener {
                UtilFunctions.showTimePickerEnable(binding.root.context, object : UtilFunctions.IResultTimePicker {
                    override fun onTimePicker(time: String?) {
                        timeStartET.setTextEditable(time ?: "")
                    }
                })
            }
            timeEndET.setOnClickListener {
                UtilFunctions.showTimePickerEnable(binding.root.context, object : UtilFunctions.IResultTimePicker {
                    override fun onTimePicker(time: String?) {
                        timeEndET.setTextEditable(time ?: "")
                    }
                })
            }
        }
    }
}