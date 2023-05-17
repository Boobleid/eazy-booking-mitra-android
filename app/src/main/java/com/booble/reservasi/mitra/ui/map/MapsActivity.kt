package com.booble.reservasi.mitra.ui.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.service.ServiceData
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityMapsBinding
import com.booble.reservasi.mitra.ui.service_detail.ServiceDetailActivity
import com.booble.reservasi.mitra.utils.UtilConstants.MYLAT
import com.booble.reservasi.mitra.utils.UtilConstants.MYLNG
import com.booble.reservasi.mitra.utils.UtilExtensions.myToast
import com.booble.reservasi.mitra.utils.UtilFunctions
import com.booble.reservasi.mitra.utils.UtilFunctions.getCurrentMyLocation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var latLong: LatLng? = null
    private var extraServiceData: ServiceData? = null

    override fun getViewBinding() = ActivityMapsBinding.inflate(layoutInflater)

    override fun initView() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initData()
        initClick()
    }

    private fun initData() {
        extraServiceData = intent.getParcelableExtra(ServiceDetailActivity.EXTRA_SERVICE_DATA)
    }

    override fun initObservers() {
    }

    override fun showFailure(failure: DataResource.Failure) {
    }

    private fun initClick() {
        binding.saveMB.setOnClickListener {
            if (latLong != null) {
                intent.putExtra(EXTRA_LAT_LONG, latLong)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                myToast("Belum dapat menemukan titik lokasi")
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(this@MapsActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this@MapsActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            myToast("Aplikasi belum memiliki akses lokasi!")
            return
        }
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.apply {
            isTiltGesturesEnabled = false
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isIndoorLevelPickerEnabled = true
            isMapToolbarEnabled = true
            isZoomGesturesEnabled = true
            isScrollGesturesEnabled = true
            isRotateGesturesEnabled = true
            isMyLocationButtonEnabled = true
        }
        changeMyCurrentLocation()
    }

    private fun changeMyCurrentLocation() {
        if (extraServiceData != null) {
            if (!extraServiceData?.lat.isNullOrEmpty() && !extraServiceData?.lng.isNullOrEmpty()) {
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            extraServiceData?.lat?.toDouble() ?: MYLAT,
                            extraServiceData?.lng?.toDouble() ?: MYLNG
                        ), 13F
                    )
                )
                mMap.setOnCameraMoveListener {
                    latLong = mMap.cameraPosition.target
                }
            } else {
                setMyCurrentLocation()
            }
        } else {
            setMyCurrentLocation()
        }
    }

    private fun setMyCurrentLocation() {
        var myLocation: LatLng
        getCurrentMyLocation(this, object : UtilFunctions.IMylatlng {
            override fun onLoadLatlng(latLng: LatLng?, isCurrentLocation: Boolean) {
                myLocation = if (latLng != null) {
                    val lat = latLng.latitude
                    val lng = latLng.longitude
                    LatLng(lat, lng)
                } else {
                    mMap.cameraPosition.target
                }

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13F))
                mMap.setOnCameraMoveListener {
                    latLong = mMap.cameraPosition.target
                }
            }

            override fun onErroLatlng() {
                myToast("Aplikasi belum memiliki akses lokasi!")
            }
        })
    }

    companion object {
        const val EXTRA_LAT_LONG = "EXTRA_LAT_LONG"
    }
}