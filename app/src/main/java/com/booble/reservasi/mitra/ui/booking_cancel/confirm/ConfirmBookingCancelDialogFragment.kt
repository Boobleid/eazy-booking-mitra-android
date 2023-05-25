package com.booble.reservasi.mitra.ui.booking_cancel.confirm

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseDialogFragment
import com.booble.reservasi.mitra.data.model.api.DefaultApiResponse
import com.booble.reservasi.mitra.data.model.api.booking_cancel.BookingCancelRequest
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.FragmentConfirmBookingCancelDialogBinding
import com.booble.reservasi.mitra.ui.MainActivity
import com.booble.reservasi.mitra.ui.booking_cancel.BookingCancelViewModel
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.myToast
import com.booble.reservasi.mitra.utils.UtilFunctions.getCurrentTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmBookingCancelDialogFragment(private var invoice: String) : BaseDialogFragment<FragmentConfirmBookingCancelDialogBinding>() {
    private val cancelViewModel by viewModels<BookingCancelViewModel>()

    private var statusConfirm = ""

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentConfirmBookingCancelDialogBinding
        get() = FragmentConfirmBookingCancelDialogBinding::inflate

    override fun initView() {
        initClick()
        initListener()
    }

    override fun initObserver() {
        cancelViewModel.confirmBookingCancel.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> successConfirm(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun successConfirm(response: DefaultApiResponse) {
        showLoading(false)
        activity?.myToast(response.message.toString())

        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun initListener() {
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            val radioButton = binding.root.rootView.findViewById<RadioButton>(id)
            statusConfirm = radioButton.text.toString()

            if (statusConfirm == getString(R.string.ditolak)) {
                binding.reasonTV.visibility = View.VISIBLE
                binding.reasonTIL.visibility = View.VISIBLE
            } else {
                binding.reasonTV.visibility = View.GONE
                binding.reasonTIL.visibility = View.GONE
            }
        }
    }

    private fun initClick() {
        binding.apply {
            iconCloseIV.setOnClickListener { dismiss() }

            saveMB.setOnClickListener {
                if (statusConfirm.isEmpty()) {
                    context?.myToast("Status konfirmasi belum diisi")
                    return@setOnClickListener
                }

                val keterangan = binding.reasonET.text.toString()
                if (statusConfirm == getString(R.string.ditolak)) {
                    if (keterangan.isEmpty()) {
                        context?.myToast("Mohon isi keterangan penolakan")
                        return@setOnClickListener
                    }
                }

                val request = BookingCancelRequest(invoice = invoice, status = statusConfirm, alasanTolak = keterangan, waktuLokal = getCurrentTime("yyyy-MM-dd"))
                cancelViewModel.confirmBookingCancelApiCall(request)
            }
        }
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }
}