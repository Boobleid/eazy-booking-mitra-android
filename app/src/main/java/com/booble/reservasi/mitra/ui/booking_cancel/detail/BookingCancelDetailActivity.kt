package com.booble.reservasi.mitra.ui.booking_cancel.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.booking_cancel.*
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityBookingCancelDetailBinding
import com.booble.reservasi.mitra.ui.booking_cancel.BookingCancelViewModel
import com.booble.reservasi.mitra.ui.booking_cancel.confirm.ConfirmBookingCancelDialogFragment
import com.booble.reservasi.mitra.utils.UtilExtensions.setTextEditable
import com.booble.reservasi.mitra.utils.UtilFunctions
import com.booble.reservasi.mitra.utils.UtilFunctions.getCurrentTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingCancelDetailActivity : BaseActivity<ActivityBookingCancelDetailBinding>() {
    private val cancelBookingViewModel by viewModels<BookingCancelViewModel>()

    private var extraBooking: BookingCancelItem? = null

    private var conversationList: ArrayList<ConversationItem> = ArrayList()

    private var isExpanded = false

    private val sessionBookingAdapter: SessionBookingCancelAdapter by lazy {
        SessionBookingCancelAdapter()
    }

    private val conversationAdapter: ConversationAdapter by lazy {
        ConversationAdapter(conversationList)
    }

    override fun getViewBinding() = ActivityBookingCancelDetailBinding.inflate(layoutInflater)

    override fun initView() {
        initData()
        initUI()
        initClick()
    }

    override fun initObservers() {
        cancelBookingViewModel.cancelConversation.observe(this) {
            when (it) {
                is DataResource.Loading -> {}
                is DataResource.Success -> showConversation(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun initData() {
        extraBooking = intent.getParcelableExtra(EXTRA_BOOKING_CANCEL) ?: BookingCancelItem()
        cancelBookingViewModel.cancelConversationApiCall(BookingCancelRequest(invoice = extraBooking?.invoice))
    }

    private fun initUI() {
        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.title = "Detail ${extraBooking?.invoice}"

            val confirmDate = extraBooking?.tglConfirmBatal
            binding.conversationRV.adapter = conversationAdapter
            binding.statusTV.text = extraBooking?.statusBatal
            binding.requestDateTV.text = extraBooking?.tglCreateBatal
            binding.confirmDateTV.text = confirmDate

            if (confirmDate?.isEmpty() == true) {
                binding.textView2.visibility = View.GONE
                binding.confirmDateTV.visibility = View.GONE
            }

            bookingDateTV.text = extraBooking?.tglBooking
            propertyNameTV.text = extraBooking?.nmProperty
            locationNameTV.text = extraBooking?.nmProduk
            reasonCancelTV.text = extraBooking?.alasanBatal
            reasonRefusalTV.text = extraBooking?.alasanTolak

            val total = extraBooking?.bayar ?: 0
            val adminFee = extraBooking?.biayaAdmin ?: 0
            val subtotal = total - adminFee
            priceTotalTV.text = UtilFunctions.formatRupiah(subtotal.toString())
            adminFeeTV.text =
                UtilFunctions.formatRupiah(UtilFunctions.isStringNullZero(extraBooking?.biayaAdmin.toString()))
            totalBillTV.text =
                UtilFunctions.formatRupiah(UtilFunctions.isStringNullZero(total.toString()))

            sessionRV.adapter = sessionBookingAdapter
            sessionBookingAdapter.submitList(extraBooking?.sesi)

            if (extraBooking?.alasanTolak?.isNotEmpty() == true) {
                binding.reasonRefusalCL.visibility = View.VISIBLE
            }

            when (extraBooking?.statusBatal) {
                "Pengajuan" -> {
                    binding.statusTV.backgroundTintList = ContextCompat.getColorStateList(
                        this@BookingCancelDetailActivity,
                        R.color.colorAccent
                    )
                    binding.confirmMB.visibility = View.VISIBLE
                }
                "Ditolak" -> {
                    binding.statusTV.backgroundTintList = ContextCompat.getColorStateList(
                        this@BookingCancelDetailActivity,
                        R.color.colorRed
                    )
                    binding.confirmMB.visibility = View.GONE
                }
                else -> {
                    binding.statusTV.backgroundTintList = ContextCompat.getColorStateList(
                        this@BookingCancelDetailActivity,
                        R.color.colorGreen
                    )
                    binding.confirmMB.visibility = View.GONE
                }
            }
        }
    }

    private fun initClick() {
        binding.bookingInfoCL.setOnClickListener {
            if (isExpanded) {
                isExpanded = false
                binding.bookingDetailCL.visibility = View.GONE
                binding.arrowIV.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_arrow_down
                    )
                )
            } else {
                isExpanded = true
                binding.bookingDetailCL.visibility = View.VISIBLE
                binding.arrowIV.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_arrow_up
                    )
                )
            }
        }

        binding.sendIV.setOnClickListener { sendMessage() }
        binding.confirmMB.setOnClickListener { openConfirmBookingCancelDialog() }
    }

    private fun showConversation(response: CancelConversationResponse) {
        binding.shimmer.visibility = View.GONE
        binding.reasonCancelCL.visibility = View.VISIBLE

        val list = response.data
        conversationList.clear()
        list?.let { conversationList.addAll(it) }
        conversationAdapter.notifyDataSetChanged()

        cancelBookingViewModel.readMessageConversationApiCall(
            BookingCancelRequest(
                invoice = extraBooking?.invoice,
                waktuLokal = getCurrentTime("yyyy-MM-dd")
            )
        )
    }

    private fun sendMessage() {
        val message = binding.messageET.text.toString()
        if (message.isEmpty()) return

        val request = SendMessageConversationRequest(
            extraBooking?.invoice,
            getCurrentTime("yyyy-MM-dd"),
            message
        )
        cancelBookingViewModel.sendMessageConversationApiCall(request)

        conversationList.add(ConversationItem(pesan = message, rightPosition = 1, leftPosition = 0))
        conversationAdapter.notifyDataSetChanged()

        binding.messageET.setTextEditable("")
        binding.messageET.clearFocus()

        cancelBookingViewModel.cancelConversationApiCall(BookingCancelRequest(invoice = extraBooking?.invoice))
    }

    private fun openConfirmBookingCancelDialog() {
        val dialogFragment = ConfirmBookingCancelDialogFragment(extraBooking?.invoice ?: "")
        dialogFragment.show(supportFragmentManager, dialogFragment.tag)
    }

    override fun showFailure(failure: DataResource.Failure) {
    }

    companion object {
        const val EXTRA_BOOKING_CANCEL = "EXTRA_BOOKING_CANCEL"
    }
}