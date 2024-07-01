package com.booble.eazybooking.mitra.ui.profile

import androidx.activity.viewModels
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.base.BaseActivity
import com.booble.eazybooking.mitra.data.model.api.user_profile.UserProfileResponse
import com.booble.eazybooking.mitra.data.model.api.user_profile.update_profile.UpdateProfileRequest
import com.booble.eazybooking.mitra.data.model.api.user_profile.update_profile.UpdateProfileResponse
import com.booble.eazybooking.mitra.data.network.DataResource
import com.booble.eazybooking.mitra.databinding.ActivityUserSettingBinding
import com.booble.eazybooking.mitra.ui.MasterViewModel
import com.booble.eazybooking.mitra.utils.UtilExceptions.handleApiError
import com.booble.eazybooking.mitra.utils.UtilExtensions.isValidate
import com.booble.eazybooking.mitra.utils.UtilExtensions.myToast
import com.booble.eazybooking.mitra.utils.UtilExtensions.setTextEditable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSettingActivity : BaseActivity<ActivityUserSettingBinding>() {
    private val userSettingViewModel by viewModels<UserSettingViewModel>()
    private val masterViewModel by viewModels<MasterViewModel>()

    override fun getViewBinding() = ActivityUserSettingBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        initClick()
    }

    private fun initClick() {
        binding.apply {
            updateMB.setOnClickListener {
                val fullName = fullNameET.text.toString()
                val userName = userNameET.text.toString()
                val email = emailET.text.toString()
                val phone = phoneET.text.toString()
                val newPassword = newPasswordET.text.toString()
                val oldPassword = oldPasswordET.text.toString()
                if (newPassword.isNotEmpty() || oldPassword.isNotEmpty()) {
                    if (newPassword.isEmpty() || oldPassword.isEmpty()){
                        myToast(getString(R.string.if_pass_change))
                        return@setOnClickListener
                    }
                }
                if (!isValidate(binding.fullNameET)) return@setOnClickListener
                if (!isValidate(binding.userNameET)) return@setOnClickListener
                if (!isValidate(binding.emailET)) return@setOnClickListener
                if (!isValidate(binding.phoneET)) return@setOnClickListener
                val request = UpdateProfileRequest(email, fullName, phone, userName, newPassword, oldPassword)
                userSettingViewModel.setUpdateProfileApiCall(request)
            }
        }
    }

    override fun initObservers() {
        masterViewModel.getUserProfileApiCall()
        masterViewModel.getUserProfile.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewUser(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
        userSettingViewModel.setUpdateProfile.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewUpdate(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    private fun showViewUpdate(response: UpdateProfileResponse) {
        showLoading(false)
        myToast(response.message.toString())
        if (response.status == true) finish()
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    private fun showViewUser(response: UserProfileResponse) {
        showLoading(false)
        binding.apply {
            fullNameET.setTextEditable(response.data?.firstName.toString())
            userNameET.setTextEditable(response.data?.username.toString())
            emailET.setTextEditable(response.data?.email.toString())
            phoneET.setTextEditable(response.data?.phone.toString())
        }
    }
}