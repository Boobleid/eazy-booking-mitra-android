package com.booble.reservasi.mitra.data.local.pref

import android.content.SharedPreferences
import com.booble.reservasi.mitra.BuildConfig
import javax.inject.Inject

/**
 * Created by rivaldy on 07/07/21
 * Find me on my Github -> https://github.com/im-o
 **/
class AppPreferencesHelper @Inject constructor(
    private val sharedPref: SharedPreferences
) : PreferencesHelper {

    override fun setUrlAPI(url: String) {
        sharedPref.edit().putString(PREF_URL_API, url).apply()
    }

    override fun getUrlAPI(): String {
        return sharedPref.getString(PREF_URL_API, null) ?: BuildConfig.BASE_URL
    }

    override fun setAccessToken(token: String) {
        sharedPref.edit().putString(PREF_KEY_ACCESS_TOKEN, token).apply()
    }

    override fun getAccessToken(): String {
        return sharedPref.getString(PREF_KEY_ACCESS_TOKEN, null) ?: ""
    }

    override fun setCurrentUserId(id: String) {
        sharedPref.edit().putString(PREF_KEY_USER_ID, id).apply()
    }

    override fun getCurrentUserId(): String {
        return sharedPref.getString(PREF_KEY_USER_ID, null) ?: ""
    }

    companion object {
        const val PREF_URL_API = "PREF_URL_API"
        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_USER_ID = "PREF_KEY_USER_ID"
    }
}