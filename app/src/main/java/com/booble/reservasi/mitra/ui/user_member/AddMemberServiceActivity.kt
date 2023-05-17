package com.booble.reservasi.mitra.ui.user_member

import android.widget.AdapterView
import androidx.activity.viewModels
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.DefaultApiResponse
import com.booble.reservasi.mitra.data.model.api.add_member.AddMemberServiceRequest
import com.booble.reservasi.mitra.data.model.api.add_member.MemberServiceData
import com.booble.reservasi.mitra.data.model.api.service.ServiceRequest
import com.booble.reservasi.mitra.data.model.api.service.ServiceResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityAddMemberServiceBinding
import com.booble.reservasi.mitra.ui.home.HomeOrderViewModel
import com.booble.reservasi.mitra.utils.UtilConstants.STR_ADD
import com.booble.reservasi.mitra.utils.UtilConstants.STR_EDIT
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isValidate
import com.booble.reservasi.mitra.utils.UtilExtensions.myToast
import com.booble.reservasi.mitra.utils.UtilExtensions.setTextEditable
import com.booble.reservasi.mitra.utils.UtilFunctions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMemberServiceActivity : BaseActivity<ActivityAddMemberServiceBinding>() {
    override fun getViewBinding() = ActivityAddMemberServiceBinding.inflate(layoutInflater)

    private val homeOrderViewModel by viewModels<HomeOrderViewModel>()
    private val addMemberServiceViewModel by viewModels<AddMemberServiceViewModel>()
    private var idService = ""
    private var idMember = ""
    private var strAction = STR_ADD
    private var extraMemberService: MemberServiceData? = null

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        initClick()
        initData()
    }

    private fun initData() {
        extraMemberService = intent.getParcelableExtra(EXTRA_MEMBER_SERVICE)
        if (extraMemberService != null) {
            binding.apply {
                strAction = STR_EDIT
                idMember = extraMemberService?.id.toString()
                idService = extraMemberService?.idProperty.toString()
                serviceACTV.setTextEditable(extraMemberService?.nmProperty.toString())
                nameET.setTextEditable(extraMemberService?.nama.toString())
                userNameET.setTextEditable(extraMemberService?.username.toString())
                emailET.setTextEditable(extraMemberService?.email.toString())
                phoneET.setTextEditable(extraMemberService?.phone.toString())
                passwordET.setTextEditable(extraMemberService?.passText.toString())
            }
        }
    }

    override fun initObservers() {
        homeOrderViewModel.getServiceApiCall(ServiceRequest(""))
        homeOrderViewModel.getServiceUser.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewService(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
        addMemberServiceViewModel.addMemberService.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewAddMember(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    private fun initClick() {
        binding.apply {
            saveMB.setOnClickListener {
                val name = nameET.text.toString()
                val username = userNameET.text.toString()
                val email = emailET.text.toString()
                val phone = phoneET.text.toString()
                val password = passwordET.text.toString()

                if (!isValidate(nameET)) return@setOnClickListener
                if (!isValidate(userNameET)) return@setOnClickListener
                if (!isValidate(emailET)) return@setOnClickListener
                if (!isValidate(phoneET)) return@setOnClickListener
                if (!isValidate(passwordET)) return@setOnClickListener
                if (idService.isEmpty()) {
                    myToast(getString(R.string.choose_service_before))
                    return@setOnClickListener
                }
                val addMemberRequest = AddMemberServiceRequest(email, idService, name, phone, password, username, strAction, idMember)
                addMemberServiceViewModel.addMemberServiceApiCall(addMemberRequest)
            }
        }
    }

    private fun showViewService(response: ServiceResponse) {
        showLoading(false)
        val listData = mutableListOf<String>()
        val adapterSpinner = UtilFunctions.setSpinner(this, listData)
        for (data in response.data ?: return) listData.add(data.nama ?: "")
        binding.serviceACTV.setAdapter(adapterSpinner)
        binding.serviceACTV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            idService = response.data?.get(position)?.id.toString()
        }
    }

    private fun showViewAddMember(response: DefaultApiResponse) {
        showLoading(false)
        myToast(response.message.toString())
        if (response.status == true) finish()
    }

    companion object {
        const val EXTRA_MEMBER_SERVICE = "EXTRA_MEMBER_SERVICE"
    }
}