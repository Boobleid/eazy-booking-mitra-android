package com.booble.reservasi.mitra.ui.login

import androidx.activity.viewModels
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.login.LoginRequest
import com.booble.reservasi.mitra.data.model.api.login.LoginResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityLoginBinding
import com.booble.reservasi.mitra.ui.MainActivity
import com.booble.reservasi.mitra.ui.MasterViewModel
import com.booble.reservasi.mitra.ui.forgot_password.ForgotPasswordActivity
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isValidate
import com.booble.reservasi.mitra.utils.UtilExtensions.openActivity
import com.booble.reservasi.mitra.utils.UtilExtensions.showSnackBar
import com.booble.reservasi.mitra.utils.UtilFunctions.oneSignalUserUID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val loginViewModel by viewModels<LoginViewModel>()
    private val masterViewModel by viewModels<MasterViewModel>()

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initView() {
        initClick()
    }

    override fun initObservers() {
        loginViewModel.loginManual.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewLogin(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    private fun showViewLogin(response: LoginResponse) {
        showLoading(false)
        if (response.status == true) {
            masterViewModel.setAccessToken(response.data?.token ?: "")
            masterViewModel.setCurrentUserId(response.data?.idUser ?: "")
            openActivity(MainActivity::class.java)
            finish()
        } else binding.root.showSnackBar(response.message.toString())
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    private fun initClick() {
        binding.loginMB.setOnClickListener {
            val strMail = binding.emailET.text.toString()
            val strPass = binding.passwordET.text.toString()
            if (!isValidate(binding.emailET)) return@setOnClickListener
            if (!isValidate(binding.passwordET)) return@setOnClickListener
            val loginRequest = LoginRequest(strPass, strMail, oneSignalUserUID)
            loginViewModel.loginManualApiCall(loginRequest)
        }
        binding.forgotPasswordTV.setOnClickListener {
            openActivity(ForgotPasswordActivity::class.java)
        }
    }
}