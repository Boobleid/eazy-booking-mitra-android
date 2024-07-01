package com.booble.eazybooking.mitra.ui.scanner

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.base.BaseActivity
import com.booble.eazybooking.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.eazybooking.mitra.data.model.api.check_history.CheckOutHistoryResponse
import com.booble.eazybooking.mitra.data.network.DataResource
import com.booble.eazybooking.mitra.databinding.ActivityScannerBinding
import com.booble.eazybooking.mitra.databinding.DialogFailedScannerBinding
import com.booble.eazybooking.mitra.ui.booking_history.BookingHistoryViewModel
import com.booble.eazybooking.mitra.ui.booking_history.detail.BookingHistoryDetailActivity
import com.booble.eazybooking.mitra.utils.UtilConstants
import com.booble.eazybooking.mitra.utils.UtilConstants.CODE_FIRST_INV
import com.booble.eazybooking.mitra.utils.UtilExceptions.handleApiError
import com.booble.eazybooking.mitra.utils.UtilExtensions.myToast
import com.booble.eazybooking.mitra.utils.UtilExtensions.openActivity
import com.booble.eazybooking.mitra.utils.UtilExtensions.setTextEditable
import com.booble.eazybooking.mitra.utils.UtilFunctions.isStringNull
import com.google.zxing.Result
import dagger.hilt.android.AndroidEntryPoint
import me.dm7.barcodescanner.zxing.ZXingScannerView

@AndroidEntryPoint
class ScannerActivity : BaseActivity<ActivityScannerBinding>(), ZXingScannerView.ResultHandler {
    private val historyViewModel by viewModels<BookingHistoryViewModel>()
    private var isLight = true
    private lateinit var dialogFailed: Dialog

    override fun getViewBinding() = ActivityScannerBinding.inflate(layoutInflater)

    override fun initView() {
        setupListener()
        doRequestPermission()
        setupDialogFailed()
        if (!hasFlash()) {
            binding.ivFlash.visibility = View.GONE
        }
    }

    override fun initObservers() {
        historyViewModel.getCheckOutHistory.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> openBookingDetail(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun openBookingDetail(response: CheckOutHistoryResponse) {
        val countData = response.data?.size ?: 0
        if (countData > 0) {
            val data = response.data?.get(0)
            openActivity(BookingHistoryDetailActivity::class.java) {
                putParcelable(BookingHistoryDetailActivity.EXTRA_CHECK_OUT_HISTORY, data)
            }
            finish()
        } else {
            myToast("Data tidak ditemukan")
        }
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    override fun handleResult(result: Result?) {
        val code = result?.text
        if (isStringNull(code).isNotEmpty()) {
            binding.idBookingET.setTextEditable(code.toString())
            doScanning()
        } else {
            dialogFailed.show()
            myToast(getString(R.string.maaf_data_tidak_ditemukan))
            binding.scanner.resumeCameraPreview(this)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.scanner.setResultHandler(this)
        binding.scanner.startCamera()
    }

    override fun onPause() {
        super.onPause()
        binding.scanner.stopCamera()
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener { onBackPressed() }
            ivFlash.setOnClickListener { switchFlashlight() }
            doneMB.setOnClickListener {
                doScanning()
            }
        }
    }

    private fun doScanning() {
        val idBooking = binding.idBookingET.text.toString().replace(CODE_FIRST_INV, "")
        if (idBooking.isEmpty()) {
            myToast(getString(R.string.required_scan_qr))
            return
        }
        val request = DefaultLimitOffsetRequest(
            UtilConstants.LIMIT_VALUE,
            UtilConstants.OFFSET_VALUE,
            idBooking
        )
        historyViewModel.getCheckOutHistoryApiCall(request)
    }

    private fun hasFlash() =
        applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

    private fun switchFlashlight() {
        if (isLight) {
            binding.scanner.flash = true
            isLight = false
            binding.ivFlash.setImageResource(R.drawable.ic_flash_off)
        } else {
            binding.scanner.flash = false
            isLight = true
            binding.ivFlash.setImageResource(R.drawable.ic_flash_on)

        }
    }

    private fun doRequestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                MY_PERMISSIONS_REQUEST
            )
        }
    }

    private fun setupDialogFailed() {
        val bindingDialog = DialogFailedScannerBinding.inflate(layoutInflater)
        dialogFailed = Dialog(this)
        dialogFailed.setContentView(bindingDialog.root)
        dialogFailed.setCancelable(false)
        dialogFailed.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogFailed.apply {
            setContentView(bindingDialog.root)
            setCanceledOnTouchOutside(false)
        }

        bindingDialog.mbBack.setOnClickListener {
            dialogFailed.dismiss()
            binding.scanner.resumeCameraPreview(this)
        }
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST = 100
    }
}