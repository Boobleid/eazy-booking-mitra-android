package com.booble.reservasi.mitra.ui.booking_history.detail

import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.check_history.CheckOutHistoryData
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityCheckOutHistoryDetailBinding
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilFunctions.formatRupiah
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNullZero
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingHistoryDetailActivity : BaseActivity<ActivityCheckOutHistoryDetailBinding>() {
    private var extraCheckOutHistory: CheckOutHistoryData? = null

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
            binding.apply {
                invoiceTV.text = getString(R.string.invoice_, extraCheckOutHistory?.invoice ?: getString(R.string.dash))
                checkInTV.text = extraCheckOutHistory?.tglCheckin ?: getString(R.string.dash)
                checkOutTV.text = extraCheckOutHistory?.tglCheckout ?: getString(R.string.dash)
                statusTV.text = extraCheckOutHistory?.status ?: getString(R.string.dash)
                userCodeTV.text = extraCheckOutHistory?.memberCode ?: getString(R.string.dash)
                userTV.text = extraCheckOutHistory?.nama ?: getString(R.string.dash)
                emailTV.text = extraCheckOutHistory?.email ?: getString(R.string.dash)
                phoneTV.text = extraCheckOutHistory?.telp ?: getString(R.string.dash)
                payToMillennialTV.text = formatRupiah(isStringNullZero(extraCheckOutHistory?.bayarMillennial))
                payToMitraTV.text = formatRupiah(isStringNullZero(extraCheckOutHistory?.bayarMitra))
            }
        }
    }

    companion object {
        const val EXTRA_CHECK_OUT_HISTORY = "EXTRA_CHECK_OUT_HISTORY"
    }
}