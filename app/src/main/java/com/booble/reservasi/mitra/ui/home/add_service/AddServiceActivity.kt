package com.booble.reservasi.mitra.ui.home.add_service

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import androidx.activity.viewModels
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.back_up.ui.finis_message.FinishMessageDialogFragment
import com.booble.reservasi.mitra.back_up.ui.finis_message.FinishMessageDialogListener
import com.booble.reservasi.mitra.back_up.ui.home.detail_order.DetailOrderActivity
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.master.category.CategoryResponse
import com.booble.reservasi.mitra.data.model.api.master.city.CityResponse
import com.booble.reservasi.mitra.data.model.api.service.ServiceData
import com.booble.reservasi.mitra.data.model.api.service.add_service.AddServiceRequest
import com.booble.reservasi.mitra.data.model.api.service.add_service.AddServiceResponse
import com.booble.reservasi.mitra.data.model.offline.FinishMessageData
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityAddServiceBinding
import com.booble.reservasi.mitra.ui.MasterViewModel
import com.booble.reservasi.mitra.ui.map.MapsActivity
import com.booble.reservasi.mitra.ui.service_detail.ServiceDetailActivity
import com.booble.reservasi.mitra.utils.UtilConstants.INT_SIZE_500_KB
import com.booble.reservasi.mitra.utils.UtilConstants.INT_SIZE_640_PX
import com.booble.reservasi.mitra.utils.UtilConstants.STR_ADD
import com.booble.reservasi.mitra.utils.UtilConstants.STR_EDIT
import com.booble.reservasi.mitra.utils.UtilCoroutines.main
import com.booble.reservasi.mitra.utils.UtilExceptions.handleApiError
import com.booble.reservasi.mitra.utils.UtilExtensions.isValidate
import com.booble.reservasi.mitra.utils.UtilExtensions.myToast
import com.booble.reservasi.mitra.utils.UtilExtensions.setTextEditable
import com.booble.reservasi.mitra.utils.UtilFunctions.encodeImageBase64
import com.booble.reservasi.mitra.utils.UtilFunctions.loge
import com.booble.reservasi.mitra.utils.UtilFunctions.setSpinner
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddServiceActivity : BaseActivity<ActivityAddServiceBinding>(), FinishMessageDialogListener {
    private val masterViewModel by viewModels<MasterViewModel>()
    private val addServiceViewModel by viewModels<AddServiceViewModel>()
    private var base64IdCard = ""
    private var strAction = STR_ADD
    private var idService = ""
    private var idCity = ""
    private var idCategory = ""
    private var extraServiceData: ServiceData? = null

    override fun getViewBinding() = ActivityAddServiceBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        initData()
        initClick()
    }

    private fun initData() {
        extraServiceData = intent.getParcelableExtra(ServiceDetailActivity.EXTRA_SERVICE_DATA)
        if (extraServiceData != null) {
            val strUrl = extraServiceData?.foto ?: ""
            strAction = STR_EDIT
            idService = extraServiceData?.id.toString()
            idCity = extraServiceData?.idKota.toString()
            idCategory = extraServiceData?.idKategori.toString()
            binding.imageStatusTV.text = strUrl

            binding.apply {
                nameET.setTextEditable(extraServiceData?.nama.toString())
                latitudeET.setTextEditable(extraServiceData?.lat.toString())
                longitudeET.setTextEditable(extraServiceData?.lng.toString())
                addressET.setTextEditable(extraServiceData?.alamat.toString())
            }
        }
    }

    override fun initObservers() {
        masterViewModel.getCityApiCall()
        masterViewModel.getServiceCategoryApiCall()

        masterViewModel.getCity.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewCity(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })

        masterViewModel.getServiceCategory.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewCategory(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })

        addServiceViewModel.addService.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewAddService(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    private fun initClick() {
        binding.apply {
            saveMB.setOnClickListener {
                if (!isValidate(nameET)) return@setOnClickListener
                if (!isValidate(addressET)) return@setOnClickListener
                //if (!isValidate(latitudeET)) return@setOnClickListener
                //if (!isValidate(longitudeET)) return@setOnClickListener
                if (extraServiceData != null) {
                    if (base64IdCard.isEmpty()) {
                        base64IdCard = ""
                    }
                } else {
                    if (base64IdCard.isEmpty()) {
                        myToast(getString(R.string.upload_image_before))
                        return@setOnClickListener
                    }
                }
                if (idCity.isEmpty()) {
                    myToast(getString(R.string.select_city_before))
                    return@setOnClickListener
                }
                if (idCategory.isEmpty()) {
                    myToast(getString(R.string.select_service_before))
                    return@setOnClickListener
                }

                val name = nameET.text.toString()
                val address = addressET.text.toString()
                val lat = latitudeET.text.toString()
                val lng = longitudeET.text.toString()
                val request = AddServiceRequest(
                    strAction, address, base64IdCard, idService, idCategory, idCity, lat, lng, name
                )
                addServiceViewModel.addServiceApiCall(request)
            }
            openImageTV.setOnClickListener {
                ImagePicker.with(this@AddServiceActivity)
                    .crop()
                    .compress(INT_SIZE_500_KB)
                    .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                    .start()
            }
            pickLocationMB.setOnClickListener {
                val intent = Intent(this@AddServiceActivity, MapsActivity::class.java)
                intent.putExtra(ServiceDetailActivity.EXTRA_SERVICE_DATA, extraServiceData)
                startActivityForResult(intent, REQUEST_PICK_MAP_CODE)
            }
        }
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    override fun onFinishMessage() {
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                if (requestCode == ImagePicker.REQUEST_CODE) {
                    val uri: Uri = data?.data!!
                    val strName = uri.path.toString()
                    val imageStream = contentResolver.openInputStream(uri)
                    val imageBitmap = BitmapFactory.decodeStream(imageStream)
                    main {
                        base64IdCard = encodeImageBase64(imageBitmap)
                    }
                    binding.imageStatusTV.text = strName
                } else if (requestCode == REQUEST_PICK_MAP_CODE) {
                    if (data != null) {
                        val latLng = data.getParcelableExtra<LatLng>(MapsActivity.EXTRA_LAT_LONG) ?: return
                        extraServiceData?.lat = latLng.latitude.toString()
                        extraServiceData?.lng = latLng.longitude.toString()
                        loge("checkLocation3 $latLng")
                        binding.latitudeET.setTextEditable(latLng.latitude.toString())
                        binding.longitudeET.setTextEditable(latLng.longitude.toString())
                    }
                }
            }
        }
    }

    private fun showViewCity(response: CityResponse) {
        showLoading(false)
        val listData = mutableListOf<String>()
        for (data in response.cityData ?: return) {
            if (idCity == data.id.toString()) binding.cityACTV.setTextEditable(data.nama.toString())
            listData.add(data.nama ?: "")
        }
        val adapterSpinner = setSpinner(this, listData)
        binding.cityACTV.setAdapter(adapterSpinner)
        binding.cityACTV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            idCity = response.cityData?.get(position)?.id.toString()
        }
    }

    private fun showViewCategory(response: CategoryResponse) {
        showLoading(false)
        val listData = mutableListOf<String>()
        for (data in response.categoryData ?: return) {
            if (idCategory == data.id.toString()) binding.categoryACTV.setTextEditable(data.nama.toString())
            listData.add(data.nama ?: "")
        }
        val adapterSpinner = setSpinner(this, listData)
        binding.categoryACTV.setAdapter(adapterSpinner)
        binding.categoryACTV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            idCategory = response.categoryData?.get(position)?.id.toString()
        }
    }

    private fun showViewAddService(response: AddServiceResponse) {
        showLoading(false)
        val finishDialogFragment = FinishMessageDialogFragment()
        finishDialogFragment.setFinishMessageDialogListener(this)
        val bundle = Bundle()
        val finishMessageData = FinishMessageData(
            response.status, response.message, "", "",
            (if (response.status == true) getString(R.string.saved_service) else getString(R.string.failed_service))
        )
        bundle.putString(FinishMessageDialogFragment.EXTRA_FINISH_MESSAGE, DetailOrderActivity.TAG)
        bundle.putParcelable(FinishMessageDialogFragment.EXTRA_FINISH_MESSAGE, finishMessageData)
        finishDialogFragment.arguments = bundle
        finishDialogFragment.show(supportFragmentManager, finishDialogFragment.tag)
    }

    companion object {
        const val REQUEST_PICK_MAP_CODE = 1001
    }
}