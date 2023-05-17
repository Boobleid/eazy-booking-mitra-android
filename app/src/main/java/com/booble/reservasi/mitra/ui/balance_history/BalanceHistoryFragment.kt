package com.booble.reservasi.mitra.ui.balance_history

import androidx.fragment.app.viewModels
import com.booble.reservasi.mitra.base.BaseFragment
import com.booble.reservasi.mitra.data.model.api.balance_history.BalanceHistoryData
import com.booble.reservasi.mitra.data.model.api.balance_history.BalanceHistoryResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.FragmentBalanceHistoryBinding
import com.booble.reservasi.mitra.ui.MasterViewModel
import com.booble.reservasi.mitra.utils.UtilConstants
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BalanceHistoryFragment : BaseFragment<FragmentBalanceHistoryBinding>(FragmentBalanceHistoryBinding::inflate) {
    private val masterViewModel by viewModels<MasterViewModel>()
    private val balanceHistoryAdapter by lazy { BalanceHistoryAdapter { item -> historyClick(item) } }

    override fun initView() {
        binding.listDataRV.adapter = balanceHistoryAdapter
        initListener()
    }

    override fun initObservers() {
        masterViewModel.getBalanceHistoryApiCall()
        masterViewModel.getBalanceHistory.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewBalance(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    private fun showViewBalance(response: BalanceHistoryResponse) {
        showLoading(false)
        binding.swipeRefresh.isRefreshing = false
        balanceHistoryAdapter.submitList(response.balanceHistoryData)
        binding.noDataTV.isVisible(response.balanceHistoryData?.size == UtilConstants.ZERO_DATA)
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            initObservers()
        }
    }

    private fun historyClick(data: BalanceHistoryData) {

    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        binding.swipeRefresh.isRefreshing = false
    }
}