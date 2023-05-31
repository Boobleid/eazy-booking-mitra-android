package com.booble.reservasi.mitra.ui

import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.booble.reservasi.mitra.MyApp.Companion.BALANCE
import com.booble.reservasi.mitra.MyApp.Companion.TYPE
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.ui.scanner.ScannerActivity
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.ErrorResponse
import com.booble.reservasi.mitra.data.model.api.booking_cancel.NumberBookingCancelResponse
import com.booble.reservasi.mitra.data.model.api.user_profile.UserProfileResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityMainBinding
import com.booble.reservasi.mitra.databinding.AppBarMainBinding
import com.booble.reservasi.mitra.ui.balance_history.BalanceHistoryFragment
import com.booble.reservasi.mitra.ui.booking_cancel.BookingCancelFragment
import com.booble.reservasi.mitra.ui.booking_history.BookingHistoryFragment
import com.booble.reservasi.mitra.ui.help.HelpActivity
import com.booble.reservasi.mitra.ui.home.HomeFragment
import com.booble.reservasi.mitra.ui.login.LoginActivity
import com.booble.reservasi.mitra.ui.profile.UserSettingActivity
import com.booble.reservasi.mitra.ui.user_member.AddMemberServiceFragment
import com.booble.reservasi.mitra.ui.withdraw.WithdrawFragment
import com.booble.reservasi.mitra.utils.UtilConstants
import com.booble.reservasi.mitra.utils.UtilConstants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.myToast
import com.booble.reservasi.mitra.utils.UtilExtensions.openActivity
import com.booble.reservasi.mitra.utils.UtilFunctions
import com.booble.reservasi.mitra.utils.UtilFunctions.checkedPermittedMap
import com.booble.reservasi.mitra.utils.UtilFunctions.formatRupiah
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNullZero
import com.booble.reservasi.mitra.utils.UtilFunctions.mapResultPermission
import com.booble.reservasi.mitra.utils.UtilFunctions.openAlertDialog
import com.booble.reservasi.mitra.utils.UtilFunctions.openAlertDialogExit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : MainListener, BaseActivity<ActivityMainBinding>() {
    private val masterViewModel by viewModels<MasterViewModel>()
    private val appBarMainBinding: AppBarMainBinding by lazy {
        binding.appBarMain
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        checkPermitted()
        setupUI()
        setupListener()
        setupListenerDrawer()
        initClick()
    }

    override fun initObservers() {
        masterViewModel.getUserProfileApiCall()
        masterViewModel.getUserProfile.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(false)
                is DataResource.Success -> showViewUser(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }

        masterViewModel.numberBookingCancelApiCall()
        masterViewModel.numberBookingCancel.observe(this) {
            when (it) {
                is DataResource.Loading -> {}
                is DataResource.Success -> checkNumberBookingCancel(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        val gson = Gson()
        val type = object : TypeToken<ErrorResponse>() {}.type
        if (failure.errorBody != null) {
            val errorResponse: ErrorResponse? = gson.fromJson(failure.errorBody.charStream(), type)
            if (errorResponse?.statusMessage?.lowercase(UtilFunctions.localeID)?.contains(UtilConstants.STR_TOKEN_EXPIRED) == true) {
                showLoading(true)
                masterViewModel.resetUserLogin()
                myToast("Sesi akun anda telah habis, silahkan login kembali")
                Handler(Looper.getMainLooper()).postDelayed({
                    showLoading(false)
                    openActivity(LoginActivity::class.java)
                    finish()
                }, 3)
            } else handleApiError(failure)
        }
    }

    private fun setupUI() {
        appBarMainBinding.tvTitleBar.text = getString(R.string.app_name)
        initDataIntent()
    }

    private fun initDataIntent() {
        val data = intent.getStringExtra(TYPE)
        if (data != null) {
            when (data) {
                BALANCE -> {
                    commitFragment(
                        BookingHistoryFragment(),
                        BookingHistoryFragment().tag.toString()
                    )
                }
                else -> {
                    commitFragment(
                        HomeFragment(), HomeFragment().tag.toString()
                    )
                }
            }
        } else {
            commitFragment(
                HomeFragment(), HomeFragment().tag.toString()
            )
        }
    }

    private fun setupListener() {
        appBarMainBinding.ivToggle.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun setupListenerDrawer() {
        with(binding.itemNav) {
            menuHomeRL.setOnClickListener {
                commitFragment(HomeFragment(), HomeFragment().tag.toString())
            }
            menuBookingHistoryRL.setOnClickListener {
                commitFragment(BookingHistoryFragment(), BookingHistoryFragment().tag.toString())
            }
            menuCancelBookingRL.setOnClickListener {
                commitFragment(BookingCancelFragment(), BookingCancelFragment().tag.toString())
            }
            menuBookingHistoryBalanceRL.setOnClickListener {
                commitFragment(BalanceHistoryFragment(), BalanceHistoryFragment().tag.toString())
            }
            menuWithdrawBalanceRL.setOnClickListener {
                commitFragment(WithdrawFragment(), WithdrawFragment().tag.toString())
            }
            menuRegisterServiceRL.setOnClickListener {
                commitFragment(
                    AddMemberServiceFragment(),
                    AddMemberServiceFragment().tag.toString()
                )
            }
            menuSettingRL.setOnClickListener {
                openActivity(ScannerActivity::class.java)
            }
            menuCallCenterRL.setOnClickListener {
                openActivity(HelpActivity::class.java)
            }
        }
    }

    private fun initClick() {
        binding.itemNav.menuSettingRL.setOnClickListener { openActivity(UserSettingActivity::class.java) }
        binding.logoutMB.setOnClickListener {
            val title = getString(R.string.exit_app)
            val message = getString(R.string.sure_exit_app)
            openAlertDialog(
                this,
                title,
                message,
                object : UtilFunctions.IDialogButtonClickListener {
                    override fun onPositiveButtonClick() {
                        showLoading(true)
                        Handler(Looper.getMainLooper()).postDelayed({
                            masterViewModel.resetUserLogin()
                            showLoading(false)
                            openActivity(LoginActivity::class.java)
                            finish()
                        }, 1000)
                    }

                    override fun onNegativeButtonClick() {
                    }
                })
        }
    }

    private fun commitFragment(fragment: Fragment, tag: String) {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, tag)
            .commit()
    }

    private fun showViewUser(response: UserProfileResponse) {
        showLoading(false)
        binding.navHeader.userNameTV.text = response.data?.firstName
        binding.navHeader.statusTV.text =
            getString(R.string.my_balance_, formatRupiah(isStringNullZero(response.data?.saldo)))
    }

    private fun checkNumberBookingCancel(response: NumberBookingCancelResponse) {
        if (response.status == true) {
            val number = response.number ?: 0
            if (number > 0) {
                binding.itemNav.numberCancelTV.visibility = View.VISIBLE
                binding.itemNav.numberCancelTV.text = number.toString()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initObservers()
    }

    override fun changeFragment() {
        commitFragment(HomeFragment(), HomeFragment().tag.toString())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (permissions.size == 1 && permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                }
            } else {
                checkPermitted()
            }
        }
    }

    private fun checkPermitted() {
        checkedPermittedMap(this, object : UtilFunctions.ICheckedPermittedMap {
            override fun onPermittedGranted() {}
            override fun onPermittedDenied() {
                val title = resources.getString(R.string.mapreq_title)
                val msg = resources.getString(R.string.mapreq_desc)
                openAlertDialogExit(this@MainActivity, title, msg, object : UtilFunctions.IDialogButtonClickListener {
                    override fun onPositiveButtonClick() {
                        mapResultPermission(this@MainActivity)
                    }

                    override fun onNegativeButtonClick() {
                        showLoading(true)
                        Handler(Looper.getMainLooper()).postDelayed({
                            showLoading(false)
                            finish()
                        }, 1000)
                    }
                })
            }
        })
    }
}