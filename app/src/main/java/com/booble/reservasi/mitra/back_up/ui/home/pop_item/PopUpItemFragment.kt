package com.booble.reservasi.mitra.back_up.ui.home.pop_item

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseDialogFragment
import com.booble.reservasi.mitra.data.model.api.booking_user.BookingUserData
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_item.BookingItemData
import com.booble.reservasi.mitra.data.model.api.booking_user.item_condition.ItemConditionRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.item_condition.ItemConditionResponse
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.FragmentPopUpItemBinding
import com.booble.reservasi.mitra.back_up.ui.home.detail_order.DetailOrderActivity
import com.booble.reservasi.mitra.utils.UtilConstants.INT_SIZE_500_KB
import com.booble.reservasi.mitra.utils.UtilConstants.INT_SIZE_640_PX
import com.booble.reservasi.mitra.utils.UtilConstants.STR_DAMAGED
import com.booble.reservasi.mitra.utils.UtilConstants.STR_GOOD
import com.booble.reservasi.mitra.utils.UtilConstants.STR_MISSED
import com.booble.reservasi.mitra.utils.UtilCoroutines.main
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.myToast
import com.booble.reservasi.mitra.utils.UtilFunctions.encodeImageBase64
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopUpItemFragment : BaseDialogFragment<FragmentPopUpItemBinding>() {
    private val popUpItemViewModel by viewModels<PopUpItemViewModel>()
    private lateinit var popUpItemListener: PopUpItemListener
    private var extraBookingItemData: BookingItemData? = null
    private var extraBookingUserData: BookingUserData? = null
    private var statusDamageItem = STR_GOOD
    private var base64IdCard = ""

    fun setPopUpItemListener(popUpItemListener: PopUpItemListener) {
        this.popUpItemListener = popUpItemListener
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPopUpItemBinding
        get() = FragmentPopUpItemBinding::inflate

    override fun initView() {
        initData()
        initClick()
    }

    private fun initData() {
        extraBookingItemData = arguments?.getParcelable(EXTRA_BOOKING_ITEM_DATA)
        extraBookingUserData = arguments?.getParcelable(DetailOrderActivity.EXTRA_BOOKING_USER_DATA)
        if (extraBookingItemData != null) {
            val descItem = getString(R.string.how_item_desc_, extraBookingItemData?.nmBarang)
            binding.apply {
                descBookingTV.text = descItem
                statusDamageItem = extraBookingItemData?.status.toString()
                when (statusDamageItem) {
                    STR_GOOD -> radioGroup.check(goodRB.id)
                    STR_DAMAGED -> radioGroup.check(damagedRB.id)
                    STR_MISSED -> radioGroup.check(missedRB.id)
                }
                verificationStatus()
            }
        }
    }

    private fun initClick() {
        binding.apply {
            submitMB.setOnClickListener {
                var desc = descriptionET.text.toString()
                val bd = extraBookingItemData
                val bu = extraBookingUserData
                if (statusDamageItem == STR_DAMAGED && base64IdCard.isEmpty()) {
                    requireActivity().myToast(getString(R.string.upload_image_before))
                    return@setOnClickListener
                }
                if (statusDamageItem != STR_DAMAGED) {
                    desc = ""
                    base64IdCard = ""
                }
                val request = ItemConditionRequest(base64IdCard, desc, bd?.id.toString(), bu?.kodeBooking, bu?.kodePembelian, statusDamageItem)
                popUpItemViewModel.verificationItemConditionApiCall(request)
            }

            radioGroup.setOnCheckedChangeListener { _, id ->
                val radioButton = binding.root.rootView.findViewById<RadioButton>(id)
                statusDamageItem = radioButton.text.toString()
                verificationStatus()
            }

            openImageTV.setOnClickListener {
                ImagePicker.with(this@PopUpItemFragment)
                    .crop()
                    .compress(INT_SIZE_500_KB)
                    .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                    .start()
            }
        }
    }

    private fun verificationStatus() {
        binding.apply {
            descriptionTIL.isVisible(statusDamageItem == STR_DAMAGED)
            openImageTV.isVisible(statusDamageItem == STR_DAMAGED)
            descImageTV.isVisible(statusDamageItem == STR_DAMAGED)
        }
    }

    override fun initObserver() {
        popUpItemViewModel.verificationItemCondition.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewVerification(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    private fun showViewVerification(response: ItemConditionResponse) {
        showLoading(false)
        requireContext().myToast(response.message.toString())
        dismiss()
        popUpItemListener.onPopUpItemMessage()
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                val strName = uri.path.toString()
                val imageStream = activity?.contentResolver?.openInputStream(uri)
                val imageBitmap = BitmapFactory.decodeStream(imageStream)
                main {
                    base64IdCard = encodeImageBase64(imageBitmap)
                }
                binding.descImageTV.text = strName
            }
        }
    }

    companion object {
        const val EXTRA_BOOKING_ITEM_DATA = "EXTRA_BOOKING_ITEM_DATA"
    }
}