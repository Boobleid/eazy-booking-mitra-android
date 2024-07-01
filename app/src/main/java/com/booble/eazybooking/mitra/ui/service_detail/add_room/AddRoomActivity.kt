package com.booble.eazybooking.mitra.ui.service_detail.add_room

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.ui.finis_message.FinishMessageDialogFragment
import com.booble.eazybooking.mitra.ui.finis_message.FinishMessageDialogListener
import com.booble.eazybooking.mitra.base.BaseActivity
import com.booble.eazybooking.mitra.data.model.api.master.facility_building.FacilityBuildingResponse
import com.booble.eazybooking.mitra.data.model.api.master.facility_room.FacilityRoomResponse
import com.booble.eazybooking.mitra.data.model.api.master.furniture.FurnitureResponse
import com.booble.eazybooking.mitra.data.model.api.service.ServiceData
import com.booble.eazybooking.mitra.data.model.api.service.add_room.AddRoomRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_room.AddRoomResponse
import com.booble.eazybooking.mitra.data.model.api.service.detail_room.DetailRoomData
import com.booble.eazybooking.mitra.data.model.offline.CheckboxData
import com.booble.eazybooking.mitra.data.model.offline.FinishMessageData
import com.booble.eazybooking.mitra.data.network.DataResource
import com.booble.eazybooking.mitra.databinding.ActivityAddRoomBinding
import com.booble.eazybooking.mitra.ui.service_detail.ServiceDetailActivity
import com.booble.eazybooking.mitra.ui.service_detail.add_room.add_furniture.AddFurnitureDialogFragment
import com.booble.eazybooking.mitra.ui.service_detail.add_room.add_furniture.AddFurnitureDialogListener
import com.booble.eazybooking.mitra.utils.NumberTextWatcher
import com.booble.eazybooking.mitra.utils.UtilConstants.INT_0
import com.booble.eazybooking.mitra.utils.UtilConstants.INT_1
import com.booble.eazybooking.mitra.utils.UtilConstants.INT_2
import com.booble.eazybooking.mitra.utils.UtilConstants.INT_3
import com.booble.eazybooking.mitra.utils.UtilConstants.INT_4
import com.booble.eazybooking.mitra.utils.UtilConstants.INT_5
import com.booble.eazybooking.mitra.utils.UtilConstants.INT_SIZE_500_KB
import com.booble.eazybooking.mitra.utils.UtilConstants.INT_SIZE_640_PX
import com.booble.eazybooking.mitra.utils.UtilConstants.STR_ADD
import com.booble.eazybooking.mitra.utils.UtilConstants.STR_EDIT
import com.booble.eazybooking.mitra.utils.UtilCoroutines.main
import com.booble.eazybooking.mitra.utils.UtilExceptions.handleApiError
import com.booble.eazybooking.mitra.utils.UtilExtensions.fromCommaToWords
import com.booble.eazybooking.mitra.utils.UtilExtensions.isValidate
import com.booble.eazybooking.mitra.utils.UtilExtensions.isVisible
import com.booble.eazybooking.mitra.utils.UtilExtensions.removeWhitespaces
import com.booble.eazybooking.mitra.utils.UtilExtensions.setTextEditable
import com.booble.eazybooking.mitra.utils.UtilFunctions.editTextNumberReplace
import com.booble.eazybooking.mitra.utils.UtilFunctions.encodeImageBase64
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRoomActivity : BaseActivity<ActivityAddRoomBinding>(), AddFurnitureDialogListener, FinishMessageDialogListener {
    private val addRoomViewModel by viewModels<AddRoomViewModel>()
    private val roomItemAdapter by lazy { CheckboxAdapter { roomItemClicked(it) } }
    private val facilityBuildingAdapter by lazy { CheckboxAdapter { facilityBuildingClicked(it) } }
    private val facilityRoomAdapter by lazy { CheckboxAdapter { facilityRoomClicked(it) } }
    private var extraServiceData: ServiceData? = null

    private var listItemId = mutableListOf<String>()
    private var listBuildingFacilityId = mutableListOf<String>()
    private var listRoomFacilityId = mutableListOf<String>()
    private var roomNumbers = mutableListOf<String>()
    private var base64Images = mutableListOf("", "", "", "", "", "")
    private var imagePosition = 0
    private var strAction = STR_ADD
    private var strRoomId = ""
    private var extraRoomData: DetailRoomData? = null

    override fun getViewBinding() = ActivityAddRoomBinding.inflate(layoutInflater)
    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        binding.layout2.listDataItemRV.adapter = roomItemAdapter
        binding.layout2.listDataFacilityBuildingRV.adapter = facilityBuildingAdapter
        binding.layout2.listDataFacilityRoomRV.adapter = facilityRoomAdapter
        initData()
        initClick()
        initListener()
    }

    private fun initListener() {
        binding.layout1.apply {
            radioGroup.setOnCheckedChangeListener { rg, id ->
                val radioButton = binding.root.rootView.findViewById<RadioButton>(id)
                val idx = rg.indexOfChild(radioButton)
                pricePerDayTIL.isVisible(idx == INT_0)
                pricePerMonthTIL.isVisible(idx == INT_0)
                pricePerYearTIL.isVisible(idx == INT_0)

                pricePerDayTV.isVisible(idx == INT_0)
                pricePerMonthTV.isVisible(idx == INT_0)
                pricePerYearTV.isVisible(idx == INT_0)

                priceBuyTIL.isVisible(idx == INT_1)
            }
            binding.maxBookingET.addTextChangedListener(NumberTextWatcher(binding.maxBookingET))
            priceBuyET.addTextChangedListener(NumberTextWatcher(priceBuyET))
            pricePerDayET.addTextChangedListener(NumberTextWatcher(pricePerDayET))
            pricePerMonthET.addTextChangedListener(NumberTextWatcher(pricePerMonthET))
            pricePerYearET.addTextChangedListener(NumberTextWatcher(pricePerYearET))
        }
    }

    override fun initObservers() {
        addRoomViewModel.getFurnitureApiCall()
        addRoomViewModel.getFacilityBuildingApiCall()
        addRoomViewModel.getFacilityRoomApiCall()
        addRoomViewModel.getFurniture.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewFurniture(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
        addRoomViewModel.getFacilityBuilding.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewFacilityBuilding(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
        addRoomViewModel.getFacilityRoom.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewFacilityRoom(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
        addRoomViewModel.addRoom.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewAddRoom(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
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
                val imageStream = contentResolver.openInputStream(uri)
                val imageBitmap = BitmapFactory.decodeStream(imageStream)
                loadImageBitmap(imagePosition, imageBitmap)
            }
        }
    }

    private fun loadImageBitmap(imagePosition: Int, url: String) {
        var imageView = binding.layout1.roomImage1IV
        when (imagePosition) {
            INT_0 -> imageView = binding.layout1.roomImage1IV
            INT_1 -> imageView = binding.layout1.roomImage2IV
            INT_2 -> imageView = binding.layout1.roomImage3IV
            INT_3 -> imageView = binding.layout1.roomImage4IV
            INT_4 -> imageView = binding.layout1.roomImage5IV
            INT_5 -> imageView = binding.layout1.roomImage6IV
        }
        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    val bitmap = (resource as BitmapDrawable).bitmap
                    loadImageBitmap(imagePosition, bitmap)
                    return false
                }
            })
            .into(imageView)
    }

    private fun loadImageBitmap(imagePosition: Int, imageBitmap: Bitmap) {
        main {
            var imageView = binding.layout1.roomImage1IV
            val base64Image = encodeImageBase64(imageBitmap)
            base64Images[imagePosition] = base64Image
            when (imagePosition) {
                INT_0 -> imageView = binding.layout1.roomImage1IV
                INT_1 -> imageView = binding.layout1.roomImage2IV
                INT_2 -> imageView = binding.layout1.roomImage3IV
                INT_3 -> imageView = binding.layout1.roomImage4IV
                INT_4 -> imageView = binding.layout1.roomImage5IV
                INT_5 -> imageView = binding.layout1.roomImage6IV
            }
            Glide.with(binding.root.context)
                .load(imageBitmap)
                .placeholder(R.color.colorDividerHigh)
                .into(imageView)
        }
    }

    private fun initData() {
        extraRoomData = intent.getParcelableExtra(EXTRA_ROOM_DATA)
        extraServiceData = intent.getParcelableExtra(ServiceDetailActivity.EXTRA_SERVICE_DATA)
        if (extraRoomData != null) {
            strRoomId = extraRoomData?.id.toString()
            strAction = STR_EDIT

            if (extraRoomData?.images != null) {
                for (i in extraRoomData?.images?.indices ?: return) {
                    if (i > base64Images.size-1) break
                    val url = extraRoomData?.images?.get(i).toString()
                    loadImageBitmap(i, url)
                }
            }
        }
    }

    private fun initClick() {
        binding.layout1.cardView1.setOnClickListener {
            imagePosition = INT_0
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }
        binding.layout1.cardView2.setOnClickListener {
            imagePosition = INT_1
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }
        binding.layout1.cardView3.setOnClickListener {
            imagePosition = INT_2
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }
        binding.layout1.cardView4.setOnClickListener {
            imagePosition = INT_3
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }
        binding.layout1.cardView5.setOnClickListener {
            imagePosition = INT_4
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }
        binding.layout1.cardView6.setOnClickListener {
            imagePosition = INT_5
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }

        binding.layout2.addItemMB.setOnClickListener {
            val addFurnitureDialogFragment = AddFurnitureDialogFragment()
            addFurnitureDialogFragment.setAddFurnitureDialogListener(this)
            addFurnitureDialogFragment.show(supportFragmentManager, addFurnitureDialogFragment.tag)
        }

        binding.apply {
            saveMB.setOnClickListener {
                val name = nameET.text.toString()
                val description = layout1.roomDescET.text.toString()
                val maxPricePerDay = editTextNumberReplace(maxBookingET)
                val bedroomCount = bedroomCountET.text.toString()
                val guestCount = guestCountET.text.toString()
                val bathRoomCount = bathRoomCountET.text.toString()
                val roomArea = roomAreaET.text.toString()
                var priceBuy = editTextNumberReplace(layout1.priceBuyET)
                val pricePerDay = editTextNumberReplace(layout1.pricePerDayET)
                val pricePerMonth = editTextNumberReplace(layout1.pricePerMonthET)
                val pricePerYear = editTextNumberReplace(layout1.pricePerYearET)
                roomNumbers = roomNumberET.text.toString().removeWhitespaces().fromCommaToWords().toMutableList()

                if (!isValidate(nameET)) return@setOnClickListener
                if (!isValidate(layout1.roomDescET)) return@setOnClickListener
                if (!isValidate(maxBookingET)) return@setOnClickListener
                if (!isValidate(bedroomCountET)) return@setOnClickListener
                if (!isValidate(guestCountET)) return@setOnClickListener
                if (!isValidate(bathRoomCountET)) return@setOnClickListener
                if (!isValidate(roomNumberET)) return@setOnClickListener
                if (!isValidate(roomAreaET)) return@setOnClickListener
                if (!isValidate(layout1.pricePerDayET)) return@setOnClickListener
                if (!isValidate(layout1.pricePerMonthET)) return@setOnClickListener
                if (!isValidate(layout1.pricePerYearET)) return@setOnClickListener

//                if (priceBuy.isEmpty() || priceBuy == ZERO_DATA.toString()) {
//                    myToast(getString(R.string.buy_price_empty))
//                    return@setOnClickListener
//                }

                priceBuy = if (priceBuy.isEmpty()) "0" else priceBuy

                val request = AddRoomRequest(
                    strAction, listItemId.distinct(), description, listBuildingFacilityId.distinct(), listRoomFacilityId.distinct(),
                    base64Images, priceBuy, pricePerDay, pricePerMonth, pricePerYear, strRoomId, extraServiceData?.id.toString(),
                    bathRoomCount, guestCount, bedroomCount, roomArea, maxPricePerDay, name, roomNumbers
                )
                addRoomViewModel.addRoomApiCall(request)
            }
        }
    }

    private fun showViewFurniture(response: FurnitureResponse) {
        showLoading(false)
        val listData = mutableListOf<CheckboxData>()
        for (data in response.furnitureData ?: return) {
            listData.add(CheckboxData(data.id.toString(), data.nama, data.nilai))
            // for checked response (name or id) == (name or id response detail)
            if (extraRoomData?.brgDlmRuangan != null) {
                for (i in extraRoomData?.brgDlmRuangan?.indices!!) {
                    val id = extraRoomData?.brgDlmRuangan!![i]
                    if (data.id.toString() == id || data.nama.toString() == id) listItemId.add(data.id.toString())
                }
            }
        }
        roomItemAdapter.setListId(listItemId)
        roomItemAdapter.submitList(listData)
        binding.layout2.noDataRoomTV.isVisible(listData.size == 0)
        setRoomView()
    }

    private fun showViewFacilityBuilding(response: FacilityBuildingResponse) {
        showLoading(false)
        val listData = mutableListOf<CheckboxData>()
        for (data in response.facilityBuildingData ?: return) {
            listData.add(CheckboxData(data.id.toString(), data.nama))
            // for checked response (name or id) == (name or id response detail)
            if (extraRoomData?.fasilitasGedung != null) {
                for (i in extraRoomData?.fasilitasGedung!!.indices) {
                    val id = extraRoomData?.fasilitasGedung!![i]
                    if (data.id.toString() == id || data.nama.toString() == id) listBuildingFacilityId.add(data.id.toString())
                }
            }
        }
        facilityBuildingAdapter.setListId(listBuildingFacilityId)
        facilityBuildingAdapter.submitList(listData)
        binding.layout2.noDataBuildingTV.isVisible(listData.size == 0)
        setRoomView()
    }

    private fun showViewFacilityRoom(response: FacilityRoomResponse) {
        showLoading(false)
        val listData = mutableListOf<CheckboxData>()
        for (data in response.facilityRoomData ?: return) {
            listData.add(CheckboxData(data.id.toString(), data.nama))
            // for checked response (name or id) == (name or id response detail)
            if (extraRoomData?.fasilitasRuangan != null) {
                for (i in extraRoomData?.fasilitasRuangan?.indices!!) {
                    val id = extraRoomData?.fasilitasRuangan!![i]
                    if (data.id.toString() == id || data.nama.toString() == id) listRoomFacilityId.add(data.id.toString())
                }
            }
        }
        facilityRoomAdapter.setListId(listRoomFacilityId)
        facilityRoomAdapter.submitList(listData)
        binding.layout2.noDataFacilityRoomTV.isVisible(listData.size == 0)
        setRoomView()
    }

    private fun showViewAddRoom(response: AddRoomResponse) {
        showLoading(false)
        showLoading(false)
        val finishDialogFragment = FinishMessageDialogFragment()
        finishDialogFragment.setFinishMessageDialogListener(this)
        val bundle = Bundle()
        val finishMessageData = FinishMessageData(
            response.status, response.message, "", "",
            (if (response.status == true) getString(R.string.saved_service) else getString(R.string.failed_service))
        )
        bundle.putParcelable(FinishMessageDialogFragment.EXTRA_FINISH_MESSAGE, finishMessageData)
        finishDialogFragment.arguments = bundle
        finishDialogFragment.show(supportFragmentManager, finishDialogFragment.tag)
    }

    override fun onCompleteAdd() {
        addRoomViewModel.getFurnitureApiCall()
    }

    override fun onFinishMessage() {
        finish()
    }

    private fun roomItemClicked(listId: MutableList<String>) {
        listItemId = listId
    }

    private fun facilityBuildingClicked(listId: MutableList<String>) {
        listBuildingFacilityId = listId
    }

    private fun facilityRoomClicked(listId: MutableList<String>) {
        listRoomFacilityId = listId
    }

    private fun setRoomView() {
        if (extraRoomData != null) {
            binding.apply {
                nameET.setTextEditable(extraRoomData?.nama ?: "")
                layout1.roomDescET.setTextEditable(extraRoomData?.deskripsi ?: "")
                maxBookingET.setTextEditable(extraRoomData?.maxBooking ?: "")
                bedroomCountET.setTextEditable(extraRoomData?.jmlTTidur ?: "")
                guestCountET.setTextEditable(extraRoomData?.jmlTamu ?: "")
                bathRoomCountET.setTextEditable(extraRoomData?.jmlKMandi ?: "")
                roomNumberET.setTextEditable(extraRoomData?.roomNumbers?.joinToString() ?: "")
                roomAreaET.setTextEditable(extraRoomData?.ukuranRuangan ?: "")
                layout1.priceBuyET.setTextEditable(extraRoomData?.hargaJual ?: "")
                layout1.pricePerDayET.setTextEditable(extraRoomData?.hargaHari ?: "")
                layout1.pricePerMonthET.setTextEditable(extraRoomData?.hargaBulan ?: "")
                layout1.pricePerYearET.setTextEditable(extraRoomData?.hargaTahun ?: "")
            }
        }
    }

    companion object {
        const val EXTRA_ROOM_DATA = ""
    }
}