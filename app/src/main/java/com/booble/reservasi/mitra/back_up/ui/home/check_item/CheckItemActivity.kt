package com.booble.reservasi.mitra.back_up.ui.home.check_item

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.booking_user.BookingUserData
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_item.BookingItemData
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_item.BookingItemRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_item.BookingItemResponse
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInRequest
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInResponse
import com.booble.reservasi.mitra.data.model.offline.FinishMessageData
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityCheckItemBinding
import com.booble.reservasi.mitra.ui.MainActivity
import com.booble.reservasi.mitra.back_up.ui.finis_message.FinishMessageDialogFragment
import com.booble.reservasi.mitra.back_up.ui.finis_message.FinishMessageDialogListener
import com.booble.reservasi.mitra.back_up.ui.home.detail_order.DetailOrderActivity
import com.booble.reservasi.mitra.back_up.ui.home.pop_item.PopUpItemFragment
import com.booble.reservasi.mitra.back_up.ui.home.pop_item.PopUpItemListener
import com.booble.reservasi.mitra.utils.UtilConstants
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.myToast
import com.booble.reservasi.mitra.utils.UtilFunctions
import com.booble.reservasi.mitra.utils.UtilFunctions.formatRupiah
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNullZero
import com.booble.reservasi.mitra.utils.UtilFunctions.openAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckItemActivity : BaseActivity<ActivityCheckItemBinding>(), FinishMessageDialogListener, PopUpItemListener {
    private val checkItemViewModel by viewModels<CheckItemViewModel>()
    private var extraBookingUserData: BookingUserData? = null
    private val checkItemAdapter: CheckItemAdapter by lazy { CheckItemAdapter { item -> checkItemClick(item) } }

    override fun getViewBinding() = ActivityCheckItemBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        binding.listDataRV.adapter = checkItemAdapter

        extraBookingUserData = intent.getParcelableExtra(DetailOrderActivity.EXTRA_BOOKING_USER_DATA)
        if (extraBookingUserData != null) {
            binding.apply {
                nameTV.text = extraBookingUserData?.firstName
                roomNameTV.text = extraBookingUserData?.nmProduk
                facilityNameTV.text = extraBookingUserData?.nmProperty
                typeTV.text = extraBookingUserData?.nmProperty
            }
            loadObservers()
        }
        initClick()
    }

    private fun loadObservers() {
        if (extraBookingUserData == null) {
            myToast(getString(R.string.item_empty_reload))
            return
        }
        val request = BookingItemRequest(extraBookingUserData?.kodeBooking.toString(), extraBookingUserData?.kodePembelian.toString())
        checkItemViewModel.getItemBookingApiCall(request)
    }

    override fun initObservers() {
        checkItemViewModel.getItemBookingApiCall.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewItem(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
        checkItemViewModel.verificationCheckOut.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewCheckOut(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    private fun showViewCheckOut(response: VerificationCheckInResponse) {
        showLoading(false)
        val finishDialogFragment = FinishMessageDialogFragment()
        finishDialogFragment.setFinishMessageDialogListener(this)
        val bundle = Bundle()
        val finishMessageData = FinishMessageData(response.status, response.message, UtilConstants.STR_CHECK_IN, response.dataBooking1?.statusBooking.toString())
        bundle.putString(FinishMessageDialogFragment.EXTRA_FINISH_MESSAGE, DetailOrderActivity.TAG)
        bundle.putParcelable(FinishMessageDialogFragment.EXTRA_FINISH_MESSAGE, finishMessageData)
        finishDialogFragment.arguments = bundle
        finishDialogFragment.show(supportFragmentManager, finishDialogFragment.tag)
    }

    private fun showViewItem(response: BookingItemResponse) {
        showLoading(false)
        binding.valueNominalTV.text = formatRupiah(isStringNullZero(response.totalDamagedGoods?.total))
        binding.noDataTV.isVisible(response.bookingItemData?.size == UtilConstants.ZERO_DATA)
        checkItemAdapter.submitList(response.bookingItemData)
    }

    private fun initClick() {
        binding.verificationMB.setOnClickListener {
            val title = getString(R.string.process_check_out)
            val message = getString(R.string.sure_check_out)
            openAlertDialog(this, title, message, object : UtilFunctions.IDialogButtonClickListener {
                override fun onPositiveButtonClick() {
                    val request = VerificationCheckInRequest(extraBookingUserData?.kodeBooking, extraBookingUserData?.kodePembelian)
                    checkItemViewModel.verificationCheckOutApiCall(request)
                }

                override fun onNegativeButtonClick() {

                }
            })
        }
    }

    private fun checkItemClick(data: BookingItemData) {
        showLoading(false)
        val popUpItemFragment = PopUpItemFragment()
        popUpItemFragment.setPopUpItemListener(this)
        val bundle = Bundle()
        bundle.putParcelable(PopUpItemFragment.EXTRA_BOOKING_ITEM_DATA, data)
        bundle.putParcelable(DetailOrderActivity.EXTRA_BOOKING_USER_DATA, extraBookingUserData)
        popUpItemFragment.arguments = bundle
        popUpItemFragment.show(supportFragmentManager, popUpItemFragment.tag)
    }

    override fun onFinishMessage() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onPopUpItemMessage() {
        loadObservers()
    }
}