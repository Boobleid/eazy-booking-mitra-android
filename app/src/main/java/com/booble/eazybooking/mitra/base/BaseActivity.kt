package com.booble.eazybooking.mitra.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.booble.eazybooking.mitra.utils.UtilDialog
import com.booble.eazybooking.mitra.utils.UtilFunctions.loge

/**
 * Created by rivaldy on 01/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB
    private val progressDialog: Dialog by lazy { UtilDialog.setProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        loge("CheckActivity ${javaClass.simpleName}")

        initView()
        initObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    abstract fun getViewBinding(): VB

    abstract fun initView()

    abstract fun initObservers()

    abstract fun showFailure(failure: com.booble.eazybooking.mitra.data.network.DataResource.Failure)

    protected fun showLoading(isShown: Boolean) {
        if (isShown) showProgressDialog()
        else hideProgressDialog()
    }

    private fun showProgressDialog() {
        hideProgressDialog()
        progressDialog.show()
    }

    private fun hideProgressDialog() {
        if (progressDialog.isShowing) progressDialog.cancel()
    }
}