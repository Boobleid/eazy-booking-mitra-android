package com.booble.eazybooking.mitra.ui.service_detail.add_room.add_furniture

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.base.BaseDialogFragment
import com.booble.eazybooking.mitra.data.model.api.service.add_furniture.AddFurnitureRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_furniture.AddFurnitureResponse
import com.booble.eazybooking.mitra.data.network.DataResource
import com.booble.eazybooking.mitra.databinding.FragmentAddFurnitureDialogBinding
import com.booble.eazybooking.mitra.utils.NumberTextWatcher
import com.booble.eazybooking.mitra.utils.UtilConstants
import com.booble.eazybooking.mitra.utils.UtilConstants.STR_ADD
import com.booble.eazybooking.mitra.utils.UtilCoroutines
import com.booble.eazybooking.mitra.utils.UtilExceptions.handleApiError
import com.booble.eazybooking.mitra.utils.UtilExtensions.myToast
import com.booble.eazybooking.mitra.utils.UtilFunctions
import com.booble.eazybooking.mitra.utils.UtilFunctions.editTextNumberReplace
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFurnitureDialogFragment : BaseDialogFragment<FragmentAddFurnitureDialogBinding>() {
    private val addFurnitureDialogViewModel by viewModels<AddFurnitureDialogViewModel>()
    private lateinit var addFurnitureDialogListener: AddFurnitureDialogListener
    private var base64IdCard = ""
    private var strAction = STR_ADD
    private var extraItemId = ""

    fun setAddFurnitureDialogListener(addFurnitureDialogListener: AddFurnitureDialogListener) {
        this.addFurnitureDialogListener = addFurnitureDialogListener
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddFurnitureDialogBinding
        get() = FragmentAddFurnitureDialogBinding::inflate

    override fun initView() {
        initClick()
        initListener()
    }

    override fun initObserver() {
        addFurnitureDialogViewModel.addFurniture.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewAddFurniture(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
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
                UtilCoroutines.main {
                    base64IdCard = UtilFunctions.encodeImageBase64(imageBitmap)
                }
                binding.imageStatusTV.text = strName
            }
        }
    }

    private fun showViewAddFurniture(response: AddFurnitureResponse) {
        activity?.myToast(response.message.toString())
        showLoading(false)
        addFurnitureDialogListener.onCompleteAdd()
        dismiss()
    }

    private fun initClick() {
        binding.apply {
            iconCloseIV.setOnClickListener { dismiss() }
            saveMB.setOnClickListener {
                val name = itemNameET.text.toString()
                val price = editTextNumberReplace(itemPriceET)
                if (name.isEmpty()) {
                    context?.myToast(getString(R.string.form_empty))
                    return@setOnClickListener
                }
                if (price.isEmpty()) {
                    context?.myToast(getString(R.string.form_empty))
                    return@setOnClickListener
                }
                val request = AddFurnitureRequest(strAction, base64IdCard, extraItemId, name, price)
                addFurnitureDialogViewModel.addFurnitureApiCall(request)
            }

            openImageTV.setOnClickListener {
                ImagePicker.with(this@AddFurnitureDialogFragment)
                    .crop()
                    .compress(UtilConstants.INT_SIZE_500_KB)
                    .maxResultSize(UtilConstants.INT_SIZE_640_PX, UtilConstants.INT_SIZE_640_PX)
                    .start()
            }
        }
    }

    private fun initListener() {
        binding.itemPriceET.addTextChangedListener(NumberTextWatcher(binding.itemPriceET))
    }
}