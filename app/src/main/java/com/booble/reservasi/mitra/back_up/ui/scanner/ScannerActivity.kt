package com.booble.reservasi.mitra.back_up.ui.scanner

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_detail.BookingDetailRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_detail.BookingDetailResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityScannerBinding
import com.booble.reservasi.mitra.databinding.DialogFailedScannerBinding
import com.booble.reservasi.mitra.back_up.ui.home.detail_order.DetailOrderViewModel
import com.booble.reservasi.mitra.back_up.ui.user_order.InfoUserOrderActivity
import com.booble.reservasi.mitra.utils.UtilConstants.CODE_FIRST_INV
import com.booble.reservasi.mitra.utils.UtilConstants.STATUS_SUCCESS
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.myToast
import com.booble.reservasi.mitra.utils.UtilExtensions.openActivity
import com.booble.reservasi.mitra.utils.UtilExtensions.setTextEditable
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNull
import com.google.zxing.Result
import dagger.hilt.android.AndroidEntryPoint
import me.dm7.barcodescanner.zxing.ZXingScannerView

@AndroidEntryPoint
class ScannerActivity : BaseActivity<ActivityScannerBinding>(), ZXingScannerView.ResultHandler {
    private val detailOrderViewModel by viewModels<DetailOrderViewModel>()
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
        detailOrderViewModel.getBookingUserDetail.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewBookingDetail(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
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
        if (idBooking.isEmpty()){
            myToast(getString(R.string.required_scan_qr))
            return
        }
        detailOrderViewModel.getBookingUserDetailApiCall(BookingDetailRequest(idBooking))
    }

    private fun hasFlash() = applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST)
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

    private fun showViewBookingDetail(response: BookingDetailResponse) {
        showLoading(false)
        if (isStringNull(response.message) == STATUS_SUCCESS) {
            openActivity(InfoUserOrderActivity::class.java) {
                putParcelable(InfoUserOrderActivity.EXTRA_BOOKING_DETAIL, response)
            }
        } else {
            myToast(response.message.toString())
            return
        }
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST = 100
    }
}