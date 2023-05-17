package com.booble.reservasi.mitra.back_up.ui.user_order

import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.booking_user.BookingUserData
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_detail.*
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityInfoUserOrderBinding
import com.booble.reservasi.mitra.back_up.ui.home.detail_order.DetailOrderActivity
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.openActivity
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNull
import com.bumptech.glide.Glide


class InfoUserOrderActivity : BaseActivity<ActivityInfoUserOrderBinding>() {
    private var extraBookingDetail: BookingDetailResponse? = null
    private lateinit var bookingData: BookingDetailData
    private lateinit var dataOther: DataOther
    private lateinit var dataMember: DataMember
    private lateinit var dataBooking: DataBooking
    private lateinit var dataTransaction: DataTransaction

    override fun getViewBinding() = ActivityInfoUserOrderBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        initData()
        initClick()
    }

    override fun initObservers() {

    }

    override fun showFailure(failure: DataResource.Failure) {

    }

    private fun initClick() {
        binding.submitMB.setOnClickListener {
            val bookingData = BookingUserData(
                status = dataBooking.status, kodeBooking = dataBooking.kodeBooking, kodePembelian = dataBooking.kodePembelian,
                createDate = dataBooking.createDate, tglCheckin = dataBooking.tglCheckin, tglCheckout = dataBooking.tglCheckout,
                firstName = dataMember.firstName, nmProduk = bookingData.nmProduk, id = bookingData.id, idMember = dataBooking.idMember,
                idTransaksi = dataBooking.idTransaksi, nmProperty = bookingData.nmProperty, username = dataMember.email
            )
            openActivity(DetailOrderActivity::class.java) {
                putParcelable(DetailOrderActivity.EXTRA_BOOKING_USER_DATA, bookingData)
            }
        }
    }

    private fun initData() {
        extraBookingDetail = intent.getParcelableExtra(EXTRA_BOOKING_DETAIL)
        if (extraBookingDetail != null) {
            bookingData = extraBookingDetail?.data ?: return
            dataOther = extraBookingDetail?.dataOther ?: return
            dataMember = extraBookingDetail?.dataMember ?: return
            dataBooking = extraBookingDetail?.dataBooking ?: return
            dataTransaction = extraBookingDetail?.dataTransaction ?: return

            binding.apply {
                hintGuestTV.isVisible(isStringNull(dataOther.ktp).length > 1)
                cardGuestCV.isVisible(isStringNull(dataOther.ktp).length > 1)

                userTV.text = dataMember.firstName
                emailTV.text = dataMember.email
                phoneTV.text = dataMember.phone

                userTypeTV.text = dataTransaction.jenisTr
                packageTV.text = if (dataTransaction.paket.isNullOrEmpty()) root.context.getText(R.string.dash) else dataTransaction.paket

                guestNameTV.text = dataOther.nmOrgLain
                Glide.with(this@InfoUserOrderActivity)
                    .load(dataBooking.gambarKtp)
                    .into(idCardUserIV)

            }
        }
    }

    companion object {
        const val EXTRA_BOOKING_DETAIL = "EXTRA_BOOKING_DETAIL"
    }
}