package com.booble.reservasi.mitra

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import com.booble.reservasi.mitra.ui.SplashScreenActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by rivaldy on 01/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        OneSignal.setNotificationOpenedHandler { result: OSNotificationOpenedResult ->
            val data = result.notification.additionalData
            setNotificationDestination(data)
        }

        FirebaseApp.initializeApp(this)
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    private fun setNotificationDestination(data: JSONObject?) {
        val intent: Intent
        data?.let {
            try {
                when (data.getString(TYPE)) {
                    BALANCE -> {
                        intent = Intent(applicationContext, SplashScreenActivity::class.java).also {
                            it.putExtra(TYPE, BALANCE)
                            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)

                    }
                    else -> {
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val ONESIGNAL_APP_ID = "31fc919b-acfe-4323-b824-a0d5ba24457c"
        const val TYPE = "type"
        const val BALANCE = "saldo"
    }
}