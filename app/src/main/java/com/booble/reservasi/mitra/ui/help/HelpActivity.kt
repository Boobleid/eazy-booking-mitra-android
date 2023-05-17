package com.booble.reservasi.mitra.ui.help

import androidx.activity.viewModels
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.help.HelpData
import com.booble.reservasi.mitra.data.model.api.help.HelpRequest
import com.booble.reservasi.mitra.data.model.api.help.HelpResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityHelpBinding
import com.booble.reservasi.mitra.ui.MasterViewModel
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HelpActivity : BaseActivity<ActivityHelpBinding>() {
    private val masterViewModel by viewModels<MasterViewModel>()
    private val helpAdapter by lazy { HelpAdapter { helpClick(it) } }

    override fun getViewBinding() = ActivityHelpBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.listDataRV.adapter = helpAdapter
    }

    override fun initObservers() {
        masterViewModel.loadListHelpApiCall(HelpRequest("Mitra", "1"))
        masterViewModel.loadListHelp.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewHelp(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    private fun showViewHelp(response: HelpResponse) {
        showLoading(false)
        binding.noDataTV.isVisible(response.data?.size == 0)
        helpAdapter.submitList(response.data)
    }

    private fun helpClick(data: HelpData) {
        openActivity(HelpDetailActivity::class.java) {
            putParcelable(HelpDetailActivity.EXTRA_HELP_DATA, data)
        }
    }
}