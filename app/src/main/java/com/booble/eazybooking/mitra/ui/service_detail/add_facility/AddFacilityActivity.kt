package com.booble.eazybooking.mitra.ui.service_detail.add_facility

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.activity.viewModels
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.base.BaseActivity
import com.booble.eazybooking.mitra.data.model.api.service.ServiceData
import com.booble.eazybooking.mitra.data.model.api.service.add_facility.AddFacilityRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_facility.AddFacilityResponse
import com.booble.eazybooking.mitra.data.model.api.service.add_facility.AddFacilitySessionData
import com.booble.eazybooking.mitra.data.model.api.service.detail_facility.DetailFacilityData
import com.booble.eazybooking.mitra.data.network.DataResource
import com.booble.eazybooking.mitra.databinding.ActivityAddFacilityBinding
import com.booble.eazybooking.mitra.ui.service_detail.ServiceDetailActivity
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
import com.booble.eazybooking.mitra.utils.UtilExtensions.isValidate
import com.booble.eazybooking.mitra.utils.UtilExtensions.isVisible
import com.booble.eazybooking.mitra.utils.UtilExtensions.myToast
import com.booble.eazybooking.mitra.utils.UtilExtensions.setTextEditable
import com.booble.eazybooking.mitra.utils.UtilFunctions
import com.booble.eazybooking.mitra.utils.UtilFunctions.getTimestamp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFacilityActivity : BaseActivity<ActivityAddFacilityBinding>() {
    private val addFacilityViewModel by viewModels<AddFacilityViewModel>()
    private val addSessionAdapter by lazy { AddSessionAdapter { sessionDeleteClick(it) } }
    private var facilitySessions = mutableListOf<AddFacilitySessionData>()
    private var base64Images = mutableListOf("", "", "", "", "", "")
    private var imagePosition = 0
    private var strAction = STR_ADD
    private var strFacilityId = ""
    private var extraServiceData: ServiceData? = null
    private var extraFacilityData: DetailFacilityData? = null

    override fun getViewBinding() = ActivityAddFacilityBinding.inflate(layoutInflater)

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) ?: return
        binding.listDataRV.adapter = addSessionAdapter
        initClick()
        initData()
        checkedSessions(facilitySessions)
    }

    private fun initData() {
        extraServiceData = intent.getParcelableExtra(ServiceDetailActivity.EXTRA_SERVICE_DATA)
        extraFacilityData = intent.getParcelableExtra(EXTRA_FACILITY_DATA)

        if (extraFacilityData != null) {
            strFacilityId = extraFacilityData?.id.toString()
            strAction = STR_EDIT

            binding.apply {
                nameET.setTextEditable(extraFacilityData?.nama.toString())
                guestMaxET.setTextEditable(extraFacilityData?.jmlTamu.toString())
                roomDescET.setTextEditable(extraFacilityData?.deskripsi.toString())
                pricePerMonthET.setTextEditable(extraFacilityData?.deskripsi.toString())
                roomDescET.setTextEditable(extraFacilityData?.deskripsi.toString())

                if (extraFacilityData?.images != null) {
                    for (i in extraFacilityData?.images?.indices ?: return) {
                        if (i > base64Images.size) break
                        val url = extraFacilityData?.images?.get(i).toString()
                        loadImageBitmap(i, url)
                    }
                }

                if (extraFacilityData?.detailFacilitySessionData != null) {
                    for (data in extraFacilityData?.detailFacilitySessionData ?: return) {
                        val facilitySession = AddFacilitySessionData(
                            data.harga.toString(), data.hari, data.jamAkhir, data.jamMulai,
                            data.sesiKe, data.kuota, getTimestamp()
                        )
                        facilitySessions.add(facilitySession)
                    }
                }
                checkedSessions(facilitySessions)
            }
        }
    }

    private fun initClick() {
        binding.saveMB.setOnClickListener {
            saveFacility()
        }

        binding.addSessionMB.setOnClickListener {
            val intSession = facilitySessions.size + 1
            val session = AddFacilitySessionData(
                sesiKe = (intSession).toString(),
                timeStamp = getTimestamp(),
                hari = mutableListOf()
            )
            facilitySessions.add(session)
            addSessionAdapter.addSessionItem(session)
//            checkedSessions(facilitySessions)
            myToast(getString(R.string.session_add_, intSession.toString()))
        }

        binding.layout.cardView1.setOnClickListener {
            imagePosition = INT_0
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }
        binding.layout.cardView2.setOnClickListener {
            imagePosition = INT_1
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }
        binding.layout.cardView3.setOnClickListener {
            imagePosition = INT_2
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }
        binding.layout.cardView4.setOnClickListener {
            imagePosition = INT_3
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }
        binding.layout.cardView5.setOnClickListener {
            imagePosition = INT_4
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }
        binding.layout.cardView6.setOnClickListener {
            imagePosition = INT_5
            ImagePicker.with(this)
                .crop()
                .compress(INT_SIZE_500_KB)
                .maxResultSize(INT_SIZE_640_PX, INT_SIZE_640_PX)
                .start()
        }
    }

    override fun initObservers() {
        addFacilityViewModel.addFacility.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewAddFacility(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun saveFacility() {
        if (!isValidate(binding.nameET)) return
        if (!isValidate(binding.roomDescET)) return

        val sessionList = addSessionAdapter.getSessions()
        if (sessionList.size == 0) {
            myToast(getString(R.string.session_empty_msg))
            return
        }

        val name = binding.nameET.text.toString()
        val desc = binding.roomDescET.text.toString()
        val guestMax = binding.guestMaxET.text.toString()
        val priceYearly = UtilFunctions.isStringNull(UtilFunctions.editTextNumberReplace(binding.pricePerYearET))
        val priceMonthly = UtilFunctions.isStringNull(UtilFunctions.editTextNumberReplace(binding.pricePerMonthET))
        val request = AddFacilityRequest(
            strAction, desc, base64Images, strFacilityId, extraServiceData?.id.toString(),
            guestMax, name, priceYearly, priceMonthly, sessionList
        )

        addFacilityViewModel.addFacilityApiCall(request)
    }

    private fun showViewAddFacility(response: AddFacilityResponse) {
        showLoading(false)
        myToast(response.message.toString())
        if (response.status == true) {
            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun sessionDeleteClick(sessions: MutableList<AddFacilitySessionData>) {
        facilitySessions = sessions
        checkedSessions(facilitySessions)
    }

    private fun checkedSessions(sessions: MutableList<AddFacilitySessionData>) {
        binding.noDataTV.isVisible(sessions.size == 0)
        addSessionAdapter.setAddSessions(sessions)
    }

    private fun loadImageBitmap(imagePosition: Int, url: String) {
        var imageView = binding.layout.image1IV
        when (imagePosition) {
            INT_0 -> imageView = binding.layout.image1IV
            INT_1 -> imageView = binding.layout.image2IV
            INT_2 -> imageView = binding.layout.image3IV
            INT_3 -> imageView = binding.layout.image4IV
            INT_4 -> imageView = binding.layout.image5IV
            INT_5 -> imageView = binding.layout.image6IV
        }
        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    val bitmap = (resource as BitmapDrawable).bitmap
                    loadImageBitmap(imagePosition, bitmap)
                    return false
                }
            })
            .into(imageView)
    }

    private fun loadImageBitmap(imagePosition: Int, imageBitmap: Bitmap) {
        main {
            var imageView = binding.layout.image1IV
            val base64Image = UtilFunctions.encodeImageBase64(imageBitmap)
            base64Images[imagePosition] = base64Image
            when (imagePosition) {
                INT_0 -> imageView = binding.layout.image1IV
                INT_1 -> imageView = binding.layout.image2IV
                INT_2 -> imageView = binding.layout.image3IV
                INT_3 -> imageView = binding.layout.image4IV
                INT_4 -> imageView = binding.layout.image5IV
                INT_5 -> imageView = binding.layout.image6IV
            }
            Glide.with(binding.root.context)
                .load(imageBitmap)
                .placeholder(R.color.colorDividerHigh)
                .into(imageView)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                val imageStream = contentResolver.openInputStream(uri)
                val imageBitmap = BitmapFactory.decodeStream(imageStream)
                main {
                    var imageView = binding.layout.image1IV
                    val base64Image = UtilFunctions.encodeImageBase64(imageBitmap)
                    base64Images[imagePosition] = base64Image
                    when (imagePosition) {
                        INT_0 -> imageView = binding.layout.image1IV
                        INT_1 -> imageView = binding.layout.image2IV
                        INT_2 -> imageView = binding.layout.image3IV
                        INT_3 -> imageView = binding.layout.image4IV
                        INT_4 -> imageView = binding.layout.image5IV
                        INT_5 -> imageView = binding.layout.image6IV
                    }
                    Glide.with(binding.root.context)
                        .load(imageBitmap)
                        .placeholder(R.color.colorDividerHigh)
                        .into(imageView)
                }
            }
        }
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        handleApiError(failure)
    }

    companion object {
        const val EXTRA_FACILITY_DATA = "EXTRA_FACILITY_DATA"
    }
}