package com.booble.reservasi.mitra.ui.withdraw

import android.content.Context
import androidx.fragment.app.viewModels
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseFragment
import com.booble.reservasi.mitra.data.model.api.DefaultApiResponse
import com.booble.reservasi.mitra.data.model.api.user_profile.UserProfileResponse
import com.booble.reservasi.mitra.data.model.api.withdraw.WithdrawRequest
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.FragmentWithdrawBinding
import com.booble.reservasi.mitra.ui.MainListener
import com.booble.reservasi.mitra.ui.MasterViewModel
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isValidate
import com.booble.reservasi.mitra.utils.UtilExtensions.myToast
import com.booble.reservasi.mitra.utils.UtilExtensions.setTextEditable
import com.booble.reservasi.mitra.utils.UtilFunctions
import com.booble.reservasi.mitra.utils.UtilFunctions.customWatcherReturn
import com.booble.reservasi.mitra.utils.UtilFunctions.editTextNumberReplace
import com.booble.reservasi.mitra.utils.UtilFunctions.formatRupiah
import com.booble.reservasi.mitra.utils.UtilFunctions.isStringNullZero
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawFragment : BaseFragment<FragmentWithdrawBinding>(FragmentWithdrawBinding::inflate) {
    private val masterViewModel by viewModels<MasterViewModel>()
    private val withdrawViewModel by viewModels<WithdrawViewModel>()
    private lateinit var mainListener: MainListener

    override fun initView() {
        initClick()
    }

    private fun initClick() {
        binding.apply {
            doMB.setOnClickListener {
                val nominal = editTextNumberReplace(nominalET)
                val bankName = bankNameET.text.toString()
                val bankNumber = bankNumberET.text.toString()
                val ownerName = ownerNameET.text.toString()

                if (!requireContext().isValidate(nominalET)) return@setOnClickListener
                if (!requireContext().isValidate(bankNameET)) return@setOnClickListener
                if (!requireContext().isValidate(bankNumberET)) return@setOnClickListener
                if (!requireContext().isValidate(ownerNameET)) return@setOnClickListener

                val request = WithdrawRequest(bankName, nominal, bankNumber, ownerName)
                withdrawViewModel.getWithdrawApiCall(request)
            }
        }
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
        withdrawViewModel.getWithdraw.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewWithdraw(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainListener = context as MainListener
    }

    private fun showViewWithdraw(response: DefaultApiResponse) {
        showLoading(false)
        requireContext().myToast(response.message.toString())
        if (response.status == true) mainListener.changeFragment()
    }

    private fun showViewUser(response: UserProfileResponse) {
        showLoading(false)
        val userData = response.data
        val balance = userData?.saldo?.toInt() ?: 0

        binding.bankNameET.setTextEditable(userData?.bank ?: "")
        binding.bankNumberET.setTextEditable(userData?.rekening ?: "")
        binding.ownerNameET.setTextEditable(userData?.pemilikRekening ?: "")

        binding.balanceTV.text = formatRupiah(isStringNullZero(balance.toString()))
        val textWatcherEstimation = customWatcherReturn(object : UtilFunctions.ITextWatcher {
            override fun onTextChanged(charSequence: CharSequence) {
                var text = charSequence.toString().trim().replace(".", "")
                if (text.isEmpty()) {
                    text = "0"
                }
                if (isStringNullZero(text).toInt() > balance) {
                    binding.nominalET.setTextEditable(balance.toString())
                    requireContext().myToast(getString(R.string.max_nominal))
                }
            }
        })
        binding.nominalET.addTextChangedListener(textWatcherEstimation)
    }
}