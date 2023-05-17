package com.booble.reservasi.mitra.ui.booking_history

import androidx.fragment.app.viewModels
import com.booble.reservasi.mitra.base.BaseFragment
import com.booble.reservasi.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.reservasi.mitra.data.model.api.check_history.CheckOutHistoryData
import com.booble.reservasi.mitra.data.model.api.check_history.CheckOutHistoryResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.FragmentCheckOutHistoryBinding
import com.booble.reservasi.mitra.ui.booking_history.detail.BookingHistoryDetailActivity
import com.booble.reservasi.mitra.utils.UtilConstants
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingHistoryFragment : BaseFragment<FragmentCheckOutHistoryBinding>(FragmentCheckOutHistoryBinding::inflate) {
    private val historyViewModel by viewModels<BookingHistoryViewModel>()
    private val checkOutHistoryAdapter by lazy { BookingHistoryAdapter { item -> historyClick(item) } }

    override fun initView() {
        binding.listDataRV.adapter = checkOutHistoryAdapter
        initListener()
    }

    override fun initObservers() {
        val request = DefaultLimitOffsetRequest(UtilConstants.LIMIT_VALUE, UtilConstants.OFFSET_VALUE)
        historyViewModel.getCheckOutHistoryApiCall(request)
        historyViewModel.getCheckOutHistory.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewHistory(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            initObservers()
        }
    }

    private fun showViewHistory(response: CheckOutHistoryResponse) {
        showLoading(false)
        binding.swipeRefresh.isRefreshing = false
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
}