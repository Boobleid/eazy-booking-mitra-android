package com.booble.eazybooking.mitra.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.booble.eazybooking.mitra.BuildConfig.REMOTE_KEY
import com.booble.eazybooking.mitra.MyApp.Companion.TYPE
import com.booble.eazybooking.mitra.base.BaseActivity
import com.booble.eazybooking.mitra.data.network.DataResource
import com.booble.eazybooking.mitra.databinding.ActivitySplashScreenBinding
import com.booble.eazybooking.mitra.ui.login.LoginActivity
import com.booble.eazybooking.mitra.utils.UtilExtensions.openActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {
    private val viewModel by viewModels<MasterViewModel>()
    private var userToken = ""

    override fun getViewBinding() = ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun initView() {
        initAPI()
    }

    override fun initObservers() {
    }

    private fun initAPI() {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isComplete) {
                    val url = remoteConfig.getString(REMOTE_KEY)
                    if (url.isNotEmpty()) viewModel.setUrlAPI(url)
                }
                checkToken()
            }
            .addOnFailureListener {
                checkToken()
            }
            .addOnCanceledListener {
                checkToken()
            }
    }

    private fun checkToken() {
        userToken = viewModel.getAccessToken()
        Handler(Looper.getMainLooper()).postDelayed({
            showLoading(false)
            if (userToken.isEmpty()) openActivity(LoginActivity::class.java)
            else initDataIntent()
            finish()
        }, 2000)
    }

    private fun initDataIntent() {
        val data = intent.getStringExtra(TYPE)
        if (data != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java).also {
                it.putExtra(TYPE, data)
                it.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        } else {
            openActivity(MainActivity::class.java)
        }
    }

    override fun showFailure(failure: DataResource.Failure) {

    }
}