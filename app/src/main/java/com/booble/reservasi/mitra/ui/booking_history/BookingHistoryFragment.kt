package com.booble.reservasi.mitra.ui.booking_history

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.booble.reservasi.mitra.base.BaseFragment
import com.booble.reservasi.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.reservasi.mitra.data.model.api.check_history.CheckOutHistoryData
import com.booble.reservasi.mitra.data.model.api.check_history.CheckOutHistoryResponse
import com.booble.reservasi.mitra.data.model.api.service.ServiceData
import com.booble.reservasi.mitra.data.model.api.service.ServiceRequest
import com.booble.reservasi.mitra.data.model.api.service.ServiceResponse
import com.booble.reservasi.mitra.data.model.offline.LocationFilter
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.FragmentCheckOutHistoryBinding
import com.booble.reservasi.mitra.ui.booking_history.calendar_booking.CalendarBookingBottomSheetFragment
import com.booble.reservasi.mitra.ui.booking_history.detail.BookingHistoryDetailActivity
import com.booble.reservasi.mitra.ui.home.HomeOrderViewModel
import com.booble.reservasi.mitra.utils.UtilConstants
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingHistoryFragment : BaseFragment<FragmentCheckOutHistoryBinding>(FragmentCheckOutHistoryBinding::inflate) {
    private val historyViewModel by viewModels<BookingHistoryViewModel>()
    private val homeOrderViewModel by activityViewModels<HomeOrderViewModel>()

    private val checkOutHistoryAdapter by lazy { BookingHistoryAdapter { item -> historyClick(item) } }

    private var location: ServiceResponse? = null //for save locationList from response API
    private var extraFilter: LocationFilter? = null
    private var query = ""

    override fun initView() {
        binding.listDataRV.adapter = checkOutHistoryAdapter

        initData()
        loadHistory()
        initClick()
        initListener()
    }

    override fun initObservers() {
        historyViewModel.getCheckOutHistory.observe(this) {
            when (it) {
                is DataResource.Loading -> {}
                is DataResource.Success -> showViewHistory(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }

        homeOrderViewModel.getServiceUser.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> {
                    showLoading(false)
                    location = it.value
                }
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun initData() {
        extraFilter = arguments?.getParcelable(EXTRA_FILTER) ?: LocationFilter()

        val request = ServiceRequest()
        homeOrderViewModel.getServiceApiCall(request)
    }

    private fun initClick() {
        binding.filterMB.setOnClickListener {
            val args = Bundle()
            val bookingBottomSheetFragment = CalendarBookingBottomSheetFragment(
                location?.data as ArrayList<ServiceData>
            ) {
                extraFilter = it
                loadHistory()
            }

            args.putParcelable(CalendarBookingBottomSheetFragment.EXTRA_FILTER, extraFilter)
            bookingBottomSheetFragment.arguments = args
            bookingBottomSheetFragment.show(childFragmentManager, bookingBottomSheetFragment.tag)
        }
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            loadHistory()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.searchProductSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                query = text ?: ""
                loadHistory()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                query = newText ?: ""
                loadHistory()
                return false
            }
        })
    }

    private fun loadHistory() {
        val request = DefaultLimitOffsetRequest(UtilConstants.LIMIT_VALUE, UtilConstants.OFFSET_VALUE, query, extraFilter?.date, extraFilter?.locationId ?: "", extraFilter?.facilityId ?: "")
        historyViewModel.getCheckOutHistoryApiCall(request)
    }

    private fun showViewHistory(response: CheckOutHistoryResponse) {
        checkOutHistoryAdapter.submitList(response.data)
        binding.noDataTV.isVisible(response.data?.size == UtilConstants.ZERO_DATA)
    }

    private fun historyClick(item: CheckOutHistoryData) {
        requireContext().openActivity(BookingHistoryDetailActivity::class.java) {
            putParcelable(BookingHistoryDetailActivity.EXTRA_CHECK_OUT_HISTORY, item)
        }
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        binding.swipeRefresh.isRefreshing = false
        handleApiError(failure)
    }

    companion object {
        const val EXTRA_FILTER = "EXTRA_FILTER"
    }
}