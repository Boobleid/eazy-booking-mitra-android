package com.booble.eazybooking.mitra.ui.booking_history.detail

import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.base.BaseActivity
import com.booble.eazybooking.mitra.data.model.api.check_history.CheckOutHistoryData
import com.booble.eazybooking.mitra.data.model.api.check_history.SessionBooking
import com.booble.eazybooking.mitra.data.network.DataResource
import com.booble.eazybooking.mitra.databinding.ActivityCheckOutHistoryDetailBinding
import com.booble.eazybooking.mitra.utils.UtilExceptions.handleApiError
import com.booble.eazybooking.mitra.utils.UtilFunctions.formatRupiah
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingHistoryDetailActivity : BaseActivity<ActivityCheckOutHistoryDetailBinding>() {
    private var extraCheckOutHistory: CheckOutHistoryData? = null

    // digunakan untuk set list sesi ketika masuk dari menu pesanan belum bayar. dibuat terpisah karena beda model
    private val sessionBookingAdapter: SessionBookingAdapter by lazy {
        SessionBookingAdapter()
    }

    override fun getViewBinding() = ActivityCheckOutHistoryDetailBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        initData()
    }

    override fun initObservers() {}

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    private fun initData() {
        extraCheckOutHistory = intent.getParcelableExtra(EXTRA_CHECK_OUT_HISTORY)
        if (extraCheckOutHistory != null) {
            initUI()
        }
    }

    private fun initUI() {
        binding.apply {
            var total = extraCheckOutHistory?.bayar ?: 0
            val adminFee = extraCheckOutHistory?.biayaAdmin ?: 0
            total -= adminFee

            invoiceTV.text = getString(
                R.string.invoice_,
                extraCheckOutHistory?.invoice ?: getString(R.string.dash)
            )
            dateTransactionTV.text = extraCheckOutHistory?.tgl ?: getString(R.string.dash)
            bookingTV.text = extraCheckOutHistory?.tglBooking ?: getString(R.string.dash)
            userTV.text = extraCheckOutHistory?.nama ?: getString(R.string.dash)
            emailTV.text = extraCheckOutHistory?.email ?: getString(R.string.dash)
            phoneTV.text = extraCheckOutHistory?.telp ?: getString(R.string.dash)
            totalTV.text = formatRupiah(total.toString())

            setSessionList(extraCheckOutHistory?.sesi)
        }
    }

    private fun setSessionList(list: List<SessionBooking>?) {
        binding.sessionBookingRV.adapter = sessionBookingAdapter
        sessionBookingAdapter.submitList(list)
    }

    companion object {
        const val EXTRA_CHECK_OUT_HISTORY = "EXTRA_CHECK_OUT_HISTORY"
    }
}