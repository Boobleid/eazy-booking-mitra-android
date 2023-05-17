package com.booble.reservasi.mitra.ui.service_detail.detail_room

import androidx.activity.viewModels
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.room_facility.RoomFacilityData
import com.booble.reservasi.mitra.data.model.api.service.detail_room.DetailRoomRequest
import com.booble.reservasi.mitra.data.model.api.service.detail_room.DetailRoomResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityRoomDetailBinding
import com.booble.reservasi.mitra.ui.service_detail.ServiceDetailActivity
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilFunctions.formatRupiah
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNullZero
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomDetailActivity : BaseActivity<ActivityRoomDetailBinding>() {
    private val roomDetailViewModel by viewModels<RoomDetailViewModel>()
    private var extraRoomFacility: RoomFacilityData? = null

    override fun getViewBinding() = ActivityRoomDetailBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        initData()
        initClick()
    }

    private fun initClick() {
        binding.actionMB.setOnClickListener { finish() }
    }

    private fun initData() {
        extraRoomFacility = intent.getParcelableExtra(ServiceDetailActivity.EXTRA_ROOM_FACILITY)
        if (extraRoomFacility != null) roomDetailViewModel.getDetailRoomApiCall(DetailRoomRequest(extraRoomFacility?.id.toString()))
    }

    override fun initObservers() {
        roomDetailViewModel.getDetailRoom.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewDetail(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    private fun showViewDetail(response: DetailRoomResponse) {
        showLoading(false)
        val item = response.detailRoomData
        binding.apply {
            itemListTV.text = if (item?.brgDlmRuangan != null) item.brgDlmRuangan?.joinToString() ?: getString(R.string.dash) else getString(R.string.dash)
            buildingFacilityTV.text = if (item?.fasilitasGedung != null) item.fasilitasGedung?.joinToString() ?: getString(R.string.dash) else getString(R.string.dash)
            roomFacilityTV.text = if (item?.fasilitasRuangan != null) item.fasilitasRuangan?.joinToString() ?: getString(R.string.dash) else getString(R.string.dash)
            roomAreaTV.text = getString(R.string.metre_m2_, item?.ukuranRuangan ?: getString(R.string.dash))
            guestCountTV.text = item?.jmlTamu ?: getString(R.string.dash)
            maxBookingTV.text = item?.maxBooking ?: getString(R.string.dash)
            bedroomCountTV.text = item?.jmlTTidur ?: getString(R.string.dash)
            bathroomCountTV.text = item?.jmlKMandi ?: getString(R.string.dash)
            moreDescTV.text = item?.deskripsi ?: getString(R.string.dash)

            rentPriceTV.text = getString(
                R.string.day_month_year_,
                formatRupiah(isStringNullZero(item?.hargaHari)),
                formatRupiah(isStringNullZero(item?.hargaBulan)),
                formatRupiah(isStringNullZero(item?.hargaTahun))
            )
            buyPriceTV.text = formatRupiah(isStringNullZero(item?.hargaJual))
        }
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }
}