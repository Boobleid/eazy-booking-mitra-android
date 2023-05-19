package com.booble.reservasi.mitra.ui.booking_history

import androidx.appcompat.widget.SearchView
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

    private var query = ""

    override fun initView() {
        binding.listDataRV.adapter = checkOutHistoryAdapter
        showLoading(true)
        initAPI()
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
    }

    private fun initAPI() {
        val request = DefaultLimitOffsetRequest(UtilConstants.LIMIT_VALUE, UtilConstants.OFFSET_VALUE, query)
        historyViewModel.getCheckOutHistoryApiCall(request)
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            initAPI()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.searchProductSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                query = text ?: ""
                initAPI()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                query = newText ?: ""
                initAPI()
                return false
            }
        })
    }

    private fun showViewHistory(response: CheckOutHistoryResponse) {
        showLoading(false)
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