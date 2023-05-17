package com.booble.reservasi.mitra.back_up.ui.finis_message

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseDialogFragment
import com.booble.reservasi.mitra.data.model.offline.FinishMessageData
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.FragmentFinishMessageDialogBinding
import com.booble.reservasi.mitra.utils.UtilExtensions.isInVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible

class FinishMessageDialogFragment : BaseDialogFragment<FragmentFinishMessageDialogBinding>() {
    private var extraFinishMessage: FinishMessageData? = null
    private var extraTypeMessage = ""
    private var isRequestSend = false
    private lateinit var finishMessageDialogListener: FinishMessageDialogListener

    fun setFinishMessageDialogListener(finishMessageDialogListener: FinishMessageDialogListener) {
        this.finishMessageDialogListener = finishMessageDialogListener
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFinishMessageDialogBinding
        get() = FragmentFinishMessageDialogBinding::inflate

    override fun initView() {
        initData()
        initClick()
    }

    override fun initObserver() {

    }

    override fun showFailure(failure: DataResource.Failure) {

    }

    override fun onDismiss(dialog: DialogInterface) {
        if (isRequestSend) {
//            if (extraTypeMessage == DoWithdrawActivity.TAG) requireActivity().finish()
//            else if (extraTypeMessage == WhatsAppContactActivity.TAG) {
//                val intent = Intent(requireActivity(), MainActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                startActivity(intent)
//            }
            finishMessageDialogListener.onFinishMessage()
        } else super.onDismiss(dialog)
    }

    private fun initData() {
        extraFinishMessage = arguments?.getParcelable(EXTRA_FINISH_MESSAGE)
        extraTypeMessage = arguments?.getString(EXTRA_TYPE_MESSAGE) ?: ""
        if (extraFinishMessage != null) {
            binding.apply {
                val strCheck = extraFinishMessage?.head
                statusBookingTV.text = strCheck
                if (extraFinishMessage?.status == false) {
                    iconSuccessIV.isInVisible(true)
                    iconFailedIV.isVisible(true)
                    descBookingTV.text = extraFinishMessage?.message
                    finishMB.text = getString(R.string.try_again)
                    isRequestSend = false
                } else {
                    statusBookingTV.text = strCheck
                    descBookingTV.text = extraFinishMessage?.message
                    finishMB.text = getString(R.string.ok)
                    isRequestSend = true
                }
            }
        }
    }

    private fun initClick() {
        binding.iconCloseIV.setOnClickListener {
            dismiss()
        }
        binding.finishMB.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val EXTRA_FINISH_MESSAGE = "EXTRA_FINISH_MESSAGE"
        const val EXTRA_TYPE_MESSAGE = "EXTRA_TYPE_MESSAGE"
    }
}