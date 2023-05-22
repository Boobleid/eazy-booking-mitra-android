package com.booble.reservasi.mitra.ui.home

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseFragment
import com.booble.reservasi.mitra.data.model.api.service.ServiceData
import com.booble.reservasi.mitra.data.model.api.service.ServiceRequest
import com.booble.reservasi.mitra.data.model.api.service.ServiceResponse
import com.booble.reservasi.mitra.data.model.offline.LocationFilter
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.FragmentHomeBinding
import com.booble.reservasi.mitra.ui.booking_history.BookingHistoryFragment
import com.booble.reservasi.mitra.ui.booking_history.calendar_booking.CalendarBookingBottomSheetFragment
import com.booble.reservasi.mitra.ui.home.add_service.AddServiceActivity
import com.booble.reservasi.mitra.ui.scanner.ScannerActivity
import com.booble.reservasi.mitra.ui.service_detail.ServiceDetailActivity
import com.booble.reservasi.mitra.utils.UtilConstants.ZERO_DATA
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.openActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeOrderViewModel by viewModels<HomeOrderViewModel>()

    private var location: ServiceResponse? = null

    private var strSearch = ""

    private val homeOrderAdapter: HomeOrderAdapter by lazy {
        HomeOrderAdapter({ item -> orderClick(item) }, { item -> editClick(item) })
    }

    override fun initView() {
        initDataUser()
        initClick()
        setupUI()
        setupListener()
    }

    override fun initObservers() {
        homeOrderViewModel.getServiceUser.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewService(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        binding.swipeRefresh.isRefreshing = false
        handleApiError(failure)
    }

    override fun onResume() {
        initDataUser()
        super.onResume()
    }

    private fun orderClick(item: ServiceData) {
        requireActivity().openActivity(ServiceDetailActivity::class.java) {
            putParcelable(ServiceDetailActivity.EXTRA_SERVICE_DATA, item)
        }
    }

    private fun editClick(item: ServiceData) {
        requireActivity().openActivity(AddServiceActivity::class.java) {
            putParcelable(ServiceDetailActivity.EXTRA_SERVICE_DATA, item)
        }
    }

    private fun initClick() {
        binding.addMB.setOnClickListener {
            requireActivity().openActivity(AddServiceActivity::class.java)
        }

        binding.scannerMB.setOnClickListener {
            requireActivity().openActivity(ScannerActivity::class.java)
        }

        binding.calendarMB.setOnClickListener {
            val bookingBottomSheetFragment = CalendarBookingBottomSheetFragment(
                location?.data as ArrayList<ServiceData>
            ) {
                openBookingHistory(it)
            }
            bookingBottomSheetFragment.show(childFragmentManager, bookingBottomSheetFragment.tag)
        }
    }

    private fun setupUI() {
        binding.listDataRV.adapter = homeOrderAdapter
    }

    private fun setupListener() {
        with(binding) {
            swipeRefresh.setOnRefreshListener {
                strSearch = ""
                binding.searchProductSV.setQuery("", false)
                initObservers()
            }

            searchProductSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    initDataUser()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    strSearch = newText.toString()
                    if (strSearch == "") initDataUser()
                    return false
                }
            })
        }
    }

    private fun initDataUser() {
        val request = ServiceRequest(strSearch)
        homeOrderViewModel.getServiceApiCall(request)
    }

    private fun showViewService(response: ServiceResponse) {
        showLoading(false)
        location = response
        binding.swipeRefresh.isRefreshing = false
        binding.noDataTV.isVisible(response.data?.size == ZERO_DATA)
        homeOrderAdapter.submitList(response.data)
    }

    private fun openBookingHistory(locationFilter: LocationFilter?) {
        val fragment = BookingHistoryFragment()
        val args = Bundle()
        args.putParcelable(BookingHistoryFragment.EXTRA_FILTER, locationFilter)
        fragment.arguments = args

        // Access the fragment manager and initiate a fragment transaction
        val fragmentManager = requireActivity().supportFragmentManager

        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, tag).commit()
    }
}