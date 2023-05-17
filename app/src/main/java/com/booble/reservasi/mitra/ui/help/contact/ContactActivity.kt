package com.booble.reservasi.mitra.ui.help.contact

import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.help.contact.ContactData
import com.booble.reservasi.mitra.data.model.api.help.contact.ContactResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityContactBinding
import com.booble.reservasi.mitra.ui.MasterViewModel
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactActivity : BaseActivity<ActivityContactBinding>() {

    private val masterViewModel by viewModels<MasterViewModel>()
    private val contactAdapter by lazy { ContactAdapter { contactClick(it) } }

    override fun getViewBinding() = ActivityContactBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.listDataRV.adapter = contactAdapter
    }

    override fun initObservers() {
        masterViewModel.loadListContactApiCall()
        masterViewModel.loadListContact.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewContact(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    private fun showViewContact(response: ContactResponse) {
        showLoading(false)
        binding.noDataTV.isVisible(response.contactData?.size == 0)
        contactAdapter.submitList(response.contactData)
    }

    private fun contactClick(data: ContactData) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url ?: "https://millennial.booblestudio.com/"))
        startActivity(intent)
    }
}