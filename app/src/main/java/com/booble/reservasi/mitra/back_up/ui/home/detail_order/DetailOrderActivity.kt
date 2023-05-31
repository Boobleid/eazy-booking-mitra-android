package com.booble.reservasi.mitra.back_up.ui.home.detail_order

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.booking_user.BookingUserData
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_detail.BookingDetailRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_detail.BookingDetailResponse
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInRequest
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInResponse
import com.booble.reservasi.mitra.data.model.offline.FinishMessageData
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityDetailOrderBinding
import com.booble.reservasi.mitra.ui.MainActivity
import com.booble.reservasi.mitra.ui.finis_message.FinishMessageDialogFragment
import com.booble.reservasi.mitra.ui.finis_message.FinishMessageDialogListener
import com.booble.reservasi.mitra.back_up.ui.home.check_item.CheckItemActivity
import com.booble.reservasi.mitra.utils.UtilConstants.STATUS_BOOKING
import com.booble.reservasi.mitra.utils.UtilConstants.STATUS_CHECK_IN
import com.booble.reservasi.mitra.utils.UtilConstants.STR_CHECK_IN
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.openActivity
import com.booble.reservasi.mitra.utils.UtilFunctions
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNull
import com.booble.reservasi.mitra.utils.UtilFunctions.openAlertDialog
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderActivity : BaseActivity<ActivityDetailOrderBinding>(), FinishMessageDialogListener {
    private val detailOrderViewModel by viewModels<DetailOrderViewModel>()
    private var extraBookingUserData: BookingUserData? = null

    override fun getViewBinding() = ActivityDetailOrderBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        initData()
        initClick()
    }

    override fun initObservers() {
        detailOrderViewModel.getBookingUserDetail.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewBookingDetail(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
        detailOrderViewModel.verificationCheckIn.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewCheckIn(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun showViewCheckIn(response: VerificationCheckInResponse) {
        showLoading(false)
        val finishDialogFragment = FinishMessageDialogFragment()
        finishDialogFragment.setFinishMessageDialogListener(this)
        val bundle = Bundle()
        val finishMessageData = FinishMessageData(response.status, response.message, STR_CHECK_IN, response.dataBooking1?.statusBooking)
        bundle.putString(FinishMessageDialogFragment.EXTRA_FINISH_MESSAGE, TAG)
        bundle.putParcelable(FinishMessageDialogFragment.EXTRA_FINISH_MESSAGE, finishMessageData)
        finishDialogFragment.arguments = bundle
        finishDialogFragment.show(supportFragmentManager, finishDialogFragment.tag)
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    private fun initData() {
        extraBookingUserData = intent.getParcelableExtra(EXTRA_BOOKING_USER_DATA)
        if (extraBookingUserData != null) {
            val request = BookingDetailRequest(extraBookingUserData?.kodeBooking.toString())
            detailOrderViewModel.getBookingUserDetailApiCall(request)
        }

        binding.checkInMB.isVisible(isStringNull(extraBookingUserData?.status) == STATUS_BOOKING)
        binding.checkOutMB.isVisible(isStringNull(extraBookingUserData?.status) == STATUS_CHECK_IN)
    }

    private fun initClick() {
        binding.checkInMB.setOnClickListener {
            val title = getString(R.string.process_check_in)
            val message = getString(R.string.sure_check_in)
            openAlertDialog(this, title, message, object : UtilFunctions.IDialogButtonClickListener {
                override fun onPositiveButtonClick() {
                    val request = VerificationCheckInRequest(extraBookingUserData?.kodeBooking, extraBookingUserData?.kodePembelian)
                    detailOrderViewModel.verificationCheckInApiCall(request)
                }

                override fun onNegativeButtonClick() {

                }
            })
        }

        binding.checkOutMB.setOnClickListener {
            openActivity(CheckItemActivity::class.java) {
                putParcelable(EXTRA_BOOKING_USER_DATA, extraBookingUserData)
            }
        }
    }

    private fun showViewBookingDetail(response: BookingDetailResponse) {
        showLoading(false)
        val data = response.data ?: return
        val dataMember = response.dataMember ?: return
        val dataBooking = response.dataBooking ?: return
        val dataTransaction = response.dataTransaction ?: return

        binding.apply {
            var date1 = dataBooking.tglCheckin
            var date2 = dataBooking.tglCheckout

            roomTypeTV.text = data.nmProduk
            apartmentNameTV.text = data.nmProperty

            userTV.text = dataMember.firstName
            emailTV.text = dataMember.email
            phoneTV.text = dataMember.phone

            if (isStringNull(dataBooking.status) == STATUS_BOOKING) {
                date1 = date2
                date2 = dataBooking.tglCheckin
                hintCheckInTV.text = getString(R.string.ordered)
                hintCheckOutTV.text = getString(R.string.check_in)
            }

            dateCheckInTV.text = date1
            dateCheckOutTV.text = date2

            userTypeTV.text = dataTransaction.jenisTr
            packageTV.text = if (dataTransaction.paket.isNullOrEmpty()) root.context.getText(R.string.dash) else dataTransaction.paket

            Glide.with(this@DetailOrderActivity)
                .load(data.banner)
                .into(sliderImage)
        }
    }

    override fun onFinishMessage() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_BOOKING_USER_DATA = "EXTRA_BOOKING_USER_DATA"
        val TAG: String = DetailOrderActivity::class.java.simpleName
    }
}