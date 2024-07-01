package com.booble.eazybooking.mitra.ui.help

import com.booble.eazybooking.mitra.base.BaseActivity
import com.booble.eazybooking.mitra.data.model.api.help.HelpData
import com.booble.eazybooking.mitra.data.network.DataResource
import com.booble.eazybooking.mitra.databinding.ActivityHelpDetailBinding
import com.booble.eazybooking.mitra.ui.help.contact.ContactActivity
import com.booble.eazybooking.mitra.utils.UtilExceptions.handleApiError
import com.booble.eazybooking.mitra.utils.UtilExtensions.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HelpDetailActivity : BaseActivity<ActivityHelpDetailBinding>() {
    private var helpData: HelpData? = null

    override fun getViewBinding() = ActivityHelpDetailBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initData()
        initClick()
    }

    override fun initObservers() {

    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    private fun initData() {
        helpData = intent.getParcelableExtra(EXTRA_HELP_DATA)
        if (helpData != null) {
            binding.titleTV.text = helpData?.pertanyaan
            binding.descriptionTV.text = helpData?.jawaban
        }
    }

    private fun initClick() {
        binding.contactUsTV.setOnClickListener {
            openActivity(ContactActivity::class.java)
        }
    }

    companion object {
        const val EXTRA_HELP_DATA = "EXTRA_HELP_DATA"
    }
}