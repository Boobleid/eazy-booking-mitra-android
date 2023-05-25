package com.booble.reservasi.mitra.ui.booking_cancel

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.booble.reservasi.mitra.base.BaseFragment
import com.booble.reservasi.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.reservasi.mitra.data.model.api.booking_cancel.BookingCancelItem
import com.booble.reservasi.mitra.data.model.api.booking_cancel.BookingCancelListRequest
import com.booble.reservasi.mitra.data.model.api.booking_cancel.BookingCancelListResponse
import com.booble.reservasi.mitra.data.model.api.service.ServiceRequest
import com.booble.reservasi.mitra.data.model.offline.LocationFilter
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.FragmentBookingCancelBinding
import com.booble.reservasi.mitra.ui.booking_cancel.detail.BookingCancelDetailActivity
import com.booble.reservasi.mitra.ui.booking_history.BookingHistoryAdapter
import com.booble.reservasi.mitra.ui.booking_history.BookingHistoryFragment
import com.booble.reservasi.mitra.ui.home.HomeOrderViewModel
import com.booble.reservasi.mitra.utils.UtilConstants
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.openActivity

class BookingCancelFragment :
    BaseFragment<FragmentBookingCancelBinding>(FragmentBookingCancelBinding::inflate) {

    private val cancelViewModel by activityViewModels<BookingCancelViewModel>()

    private val bookingCancelAdapter by lazy { BookingCancelAdapter { item -> adapterClick(item) } }

    private var query = ""

    override fun initView() {
        initData()
        initUI()
        initListener()
    }

    override fun initObservers() {
        cancelViewModel.bookingCancelList.observe(this) {
            when (it) {
                is DataResource.Loading -> {}
                is DataResource.Success -> setBookingCancelList(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun initData() {
        val request = BookingCancelListRequest(UtilConstants.LIMIT_VALUE, UtilConstants.OFFSET_VALUE, query,  "", "", "")
        cancelViewModel.bookingCancelListApiCall(request)
    }

    private fun initUI() {
        binding.listDataRV.adapter = bookingCancelAdapter
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            initData()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.bookingSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                query = text ?: ""
                initData()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                query = newText ?: ""
                initData()
                return false
            }
        })
    }

    private fun setBookingCancelList(response: BookingCancelListResponse) {
        bookingCancelAdapter.submitList(response.list)
        binding.noDataTV.isVisible(response.list?.size == UtilConstants.ZERO_DATA)
    }

    private fun adapterClick(item: BookingCancelItem) {
        requireContext().openActivity(BookingCancelDetailActivity::class.java) {
            putParcelable(BookingCancelDetailActivity.EXTRA_BOOKING_CANCEL, item)
        }
    }

    override fun showFailure(failure: DataResource.Failure) {
    }
}