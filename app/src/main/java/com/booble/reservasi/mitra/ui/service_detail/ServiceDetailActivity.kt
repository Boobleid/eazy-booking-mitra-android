package com.booble.reservasi.mitra.ui.service_detail

import androidx.activity.viewModels
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.room_facility.RoomFacilityData
import com.booble.reservasi.mitra.data.model.api.room_facility.RoomFacilityRequest
import com.booble.reservasi.mitra.data.model.api.room_facility.RoomFacilityResponse
import com.booble.reservasi.mitra.data.model.api.room_facility.status_display.StatusDisplayRoomFacilityRequest
import com.booble.reservasi.mitra.data.model.api.service.ServiceData
import com.booble.reservasi.mitra.data.model.api.service.detail_facility.DetailFacilityRequest
import com.booble.reservasi.mitra.data.model.api.service.detail_facility.DetailFacilityResponse
import com.booble.reservasi.mitra.data.model.api.service.detail_room.DetailRoomResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityServiceDetailBinding
import com.booble.reservasi.mitra.ui.service_detail.add_facility.AddFacilityActivity
import com.booble.reservasi.mitra.ui.service_detail.add_room.AddRoomActivity
import com.booble.reservasi.mitra.ui.service_detail.detail_facility.FacilityDetailActivity
import com.booble.reservasi.mitra.ui.service_detail.detail_facility.FacilityDetailViewModel
import com.booble.reservasi.mitra.utils.UtilConstants.LIMIT_VALUE
import com.booble.reservasi.mitra.utils.UtilConstants.OFFSET_VALUE
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceDetailActivity : BaseActivity<ActivityServiceDetailBinding>() {
    private val serviceDetailViewModel by viewModels<ServiceDetailViewModel>()
    private val facilityDetailViewModel by viewModels<FacilityDetailViewModel>()
    private var extraServiceData: ServiceData? = null

    private val serviceDetailAdapter: ServiceDetailAdapter by lazy {
        ServiceDetailAdapter(
            this,
            { item -> detailClick(item) },
            { item -> changeClick(item) },
            { item, status -> switchDisplayClick(item, status) })
    }

    override fun getViewBinding() = ActivityServiceDetailBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        binding.listDataRV.adapter = serviceDetailAdapter
        initData()
        initClick()
    }

    private fun initClick() {
        binding.addFacilityMB.setOnClickListener {
            openActivity(AddFacilityActivity::class.java) {
                putParcelable(EXTRA_SERVICE_DATA, extraServiceData)
            }
        }
    }

    override fun initObservers() {
        serviceDetailViewModel.getRoomFacility.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewService(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }

        facilityDetailViewModel.getDetailFacility.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewDetailFacility(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun showViewDetailRoom(response: DetailRoomResponse) {
        showLoading(false)
        if (response.status == true) {
            openActivity(AddRoomActivity::class.java) {
                putParcelable(EXTRA_SERVICE_DATA, extraServiceData)
                putParcelable(AddRoomActivity.EXTRA_ROOM_DATA, response.detailRoomData)
            }
        }
    }

    private fun showViewDetailFacility(response: DetailFacilityResponse) {
        showLoading(false)
        if (response.status == true) {
            openActivity(AddFacilityActivity::class.java) {
                putParcelable(EXTRA_SERVICE_DATA, extraServiceData)
                putParcelable(AddFacilityActivity.EXTRA_FACILITY_DATA, response.detailFacilityData)
            }
        }
    }

    private fun initData() {
        extraServiceData = intent.getParcelableExtra(EXTRA_SERVICE_DATA)
        binding.nameTV.text = extraServiceData?.nama
        binding.addressTV.text = extraServiceData?.alamat

        val request =
            RoomFacilityRequest(extraServiceData?.id.toString(), LIMIT_VALUE, OFFSET_VALUE)
        serviceDetailViewModel.getRoomFacilityApiCall(request)
    }

    private fun showViewService(response: RoomFacilityResponse) {
        showLoading(false)
        response.roomFacilityData?.let { serviceDetailAdapter.setData(it) }
        binding.noDataTV.isVisible(response.roomFacilityData?.size == 0)
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    private fun detailClick(item: RoomFacilityData) {
        openActivity(FacilityDetailActivity::class.java) {
            putParcelable(EXTRA_ROOM_FACILITY, item)
        }
    }

    private fun changeClick(item: RoomFacilityData) {
        facilityDetailViewModel.getDetailFacility(DetailFacilityRequest(item.id.toString()))
    }

    private fun switchDisplayClick(item: RoomFacilityData, status: String) {
        serviceDetailViewModel.setStatusDisplayApiCall(
            StatusDisplayRoomFacilityRequest(
                item.id.toString(),
                item.kategori,
                status
            )
        )
    }

    companion object {
        const val EXTRA_SERVICE_DATA = "EXTRA_SERVICE_DATA"
        const val EXTRA_ROOM_FACILITY = "EXTRA_ROOM_FACILITY"
    }
}