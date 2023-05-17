package com.booble.reservasi.mitra.ui.forgot_password

import androidx.activity.viewModels
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.forgot_password.ForgotPasswordRequest
import com.booble.reservasi.mitra.data.model.api.forgot_password.ForgotPasswordResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityForgotPasswordBinding
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isValidate
import com.booble.reservasi.mitra.utils.UtilExtensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding>() {
    private val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun getViewBinding() = ActivityForgotPasswordBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        initClick()
    }

    private fun initClick() {
        binding.sendMB.setOnClickListener {
            if (!isValidate(binding.emailET)) return@setOnClickListener
            val email = binding.emailET.text.toString()
            viewModel.setForgotPasswordApiCall(ForgotPasswordRequest(email))
        }
    }

    override fun initObservers() {
        viewModel.setForgotPassword.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewForgot(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    private fun showViewForgot(response: ForgotPasswordResponse) {
        showLoading(false)
        binding.root.showSnackBar(response.message.toString())
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }
}