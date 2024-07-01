package com.booble.eazybooking.mitra.ui.service_detail.detail_facility

import androidx.activity.viewModels
import com.booble.eazybooking.mitra.base.BaseActivity
import com.booble.eazybooking.mitra.data.model.api.room_facility.RoomFacilityData
import com.booble.eazybooking.mitra.data.model.api.service.detail_facility.DetailFacilityRequest
import com.booble.eazybooking.mitra.data.model.api.service.detail_facility.DetailFacilityResponse
import com.booble.eazybooking.mitra.data.network.DataResource
import com.booble.eazybooking.mitra.databinding.ActivityFacilityDetailBinding
import com.booble.eazybooking.mitra.ui.service_detail.ServiceDetailActivity
import com.booble.eazybooking.mitra.utils.UtilExceptions.handleApiError
import com.booble.eazybooking.mitra.utils.UtilExtensions.isVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FacilityDetailActivity : BaseActivity<ActivityFacilityDetailBinding>() {
    private val facilityDetailViewModel by viewModels<FacilityDetailViewModel>()
    private var extraRoomFacility: RoomFacilityData? = null
    private val facilitySessionAdapter by lazy { FacilitySessionAdapter() }

    override fun getViewBinding() = ActivityFacilityDetailBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        binding.listDataRV.adapter = facilitySessionAdapter
        initData()
        initClick()
    }

    private fun initClick() {
        binding.actionMB.setOnClickListener { finish() }
    }

    private fun initData() {
        extraRoomFacility = intent.getParcelableExtra(ServiceDetailActivity.EXTRA_ROOM_FACILITY)
        if (extraRoomFacility != null) facilityDetailViewModel.getDetailFacility(DetailFacilityRequest(extraRoomFacility?.id.toString()))
    }

    override fun initObservers() {
        facilityDetailViewModel.getDetailFacility.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewDetail(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun showViewDetail(response: DetailFacilityResponse) {
        showLoading(false)
        binding.apply {
            facilityNameTV.text = response.detailFacilityData?.nama
            facilityDescriptionTV.text = response.detailFacilityData?.deskripsi
            maxQuotaTV.text = response.detailFacilityData?.jmlTamu
        }
        facilitySessionAdapter.submitList(response.detailFacilityData?.detailFacilitySessionData)
        binding.noDataTV.isVisible(response.detailFacilityData?.detailFacilitySessionData?.size == 0)
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }
}