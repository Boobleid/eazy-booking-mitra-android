package com.booble.eazybooking.mitra.utils

import android.Manifest
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.utils.UtilConstants.LOG_MESSAGE
import com.booble.eazybooking.mitra.utils.UtilConstants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText
import com.onesignal.OneSignal
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by rivaldy on 01/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

object UtilFunctions { 

    val localeID = Locale("in", "ID")

    val oneSignalUserUID = OneSignal.getDeviceState()?.userId

    fun loge(message: String) {
        Log.e(LOG_MESSAGE, message)
    }

    fun getTimestamp(): Long {
        return Calendar.getInstance().time.time
    }

    //check permitted map
    fun checkedPermittedMap(activity: AppCompatActivity, listener: ICheckedPermittedMap) {
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            listener.onPermittedGranted()
        } else {
            listener.onPermittedDenied()
        }
    }

    fun mapResultPermission(activity: AppCompatActivity) {
        return ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        )
    }

    fun getCurrentMyLocation(context: Context, listener: IMylatlng) { //getCurrentMyLocation
        val locationManager = context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false) ?: LocationManager.GPS_PROVIDER)

        if (location != null) {
            val lat = location.latitude
            val lng = location.longitude
            val latLng = LatLng(lat, lng)
            loge("INIII getCurrentMyLocation : $lat $lng")
            listener.onLoadLatlng(latLng, true)

        } else { //getLastKnownLocation
            loge("setMyLastLocation: excecute, and get last location")
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                if (loc != null) {
                    val lat = loc.latitude
                    val lng = loc.longitude
                    val latLng = LatLng(lat, lng)
                    loge("INIII getLastKnownLocation : $lat $lng")
                    listener.onLoadLatlng(latLng, false)
                } else {
                    listener.onErroLatlng()
                }
            }
        }
    }

    fun picassoBitmap(strUrl: String, listener: IResultPicassoBitmap) {
        Picasso.get()
            .load(strUrl)
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    listener.onBitmapLoaded(bitmap)
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    listener.onBitmapFailed()
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    loge("Picasso : onPrepareLoad")
                    //listener.onBitmapFailed()
                }
            })
    }

    fun showTimePickerEnable(context: Context, resultTimePicker: IResultTimePicker) {
        val calendar = Calendar.getInstance(localeID)
        val listener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            val sdf: DateFormat = SimpleDateFormat("HH:mm", localeID)
            val strHour = sdf.format(calendar.time)
            resultTimePicker.onTimePicker(strHour)
        }
        TimePickerDialog(context, listener, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false).show()
    }

    fun editTextNumberReplace(editText: TextInputEditText): String {
        var text = editText.text.toString().replace(",", "").replace(".", "")
        if (text.isEmpty()) text = "0"
        return text
    }

    fun setSpinner(context: Context, items: MutableList<String>): ArrayAdapter<String> {
        return ArrayAdapter(context, R.layout.layout_spinner_item, items)
    }

    fun getCurrentTime(format: String): String {
        val dateFormat = SimpleDateFormat(format, localeID)
        return dateFormat.format(Calendar.getInstance().time)
    }

    fun dateToStrDate(calendar: Calendar): String {
        val sdf: DateFormat = SimpleDateFormat("yyyy-MM-dd", localeID) // 2021-08-21
        return sdf.format(calendar.time)
    }

    fun isStringNull(text: String?): String {
        return if (text != null) {
            if (text.isEmpty()) ""
            else text
        } else ""
    }

    fun isStringNullZero(text: String?): String {
        return if (text != null) {
            if (text.isEmpty()) "0"
            else text
        } else "0"
    }

    fun openAlertDialog(context: Context, title: String?, msg: String?, listener: IDialogButtonClickListener) {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
            .setTitle(title)
            .setMessage(msg)
            //.setIcon(R.drawable.ic_baseline_info_24)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                listener.onPositiveButtonClick()
            }.setNegativeButton(android.R.string.cancel) { _, _ ->
                listener.onNegativeButtonClick()
            }
        builder.create().show()
    }

    fun openAlertDialogExit(context: Context?, title: String?, msg: String?, listener: IDialogButtonClickListener) {
        val builder = android.app.AlertDialog.Builder(context, R.style.AlertDialogTheme)
            .setTitle(title)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                listener.onPositiveButtonClick()
            }.setNegativeButton(R.string.str_exit) { _, _ ->
                listener.onNegativeButtonClick()
            }
        builder.create()
        if (builder.show().isShowing) {
            //builder.show().cancel()
        } else {
            builder.show()
        }
    }

    fun toRupiahNotRp(original: String): String {
        if (original == "") {
            return ""
        }
        val ori = original.replace(".00", "")
        val currency = ori.toLong()
        return NumberFormat.getNumberInstance(localeID).format(currency).replace(",", ".")
    }

    fun formatRupiah(original: String?): String {
        val strValue: String
        if (original == "") {
            return ""
        }
        val originalReplace = original?.replace(".00", "")
        val myBalance = originalReplace?.toLong() ?: 0F
        val total = "Rp"
        val valueRp = NumberFormat.getNumberInstance(localeID).format(myBalance).replace(",", ".")
        strValue = total + valueRp
        return strValue
    }

    fun encodeImageBase64(bitmap: Bitmap): String {
        val byteArr = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArr)
        val byte = byteArr.toByteArray()
        return Base64.encodeToString(byte, Base64.DEFAULT)
    }

    fun decodeImageBase64(base64: String): Bitmap {
        val decodedString = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun customWatcherReturn(watcher: ITextWatcher): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                watcher.onTextChanged(charSequence.toString().replace(",", "."))
            }

            override fun afterTextChanged(editable: Editable) {}
        }
    }

    interface IResultTimePicker {
        fun onTimePicker(time: String?)
    }

    interface IResultPicassoBitmap {
        fun onBitmapLoaded(bitmap: Bitmap?)
        fun onBitmapFailed()
    }

    interface IDialogButtonClickListener {
        fun onPositiveButtonClick()
        fun onNegativeButtonClick()
    }

    interface ITextWatcher {
        fun onTextChanged(charSequence: CharSequence)
    }

    interface ICheckedPermittedMap {
        fun onPermittedGranted()
        fun onPermittedDenied()
    }

    interface IMylatlng {
        fun onLoadLatlng(latLng: LatLng?, isCurrentLocation: Boolean)
        fun onErroLatlng()
    }
}