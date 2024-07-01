package com.booble.eazybooking.mitra.di

import android.content.SharedPreferences
import com.booble.eazybooking.mitra.data.remote.ApiService
import com.booble.eazybooking.mitra.data.remote.AppApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by rivaldy on 10/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

@Module
@InstallIn(SingletonComponent::class)
object AppHelperModule {

    @Provides
    fun provideAppDbHelper(appDatabase: com.booble.eazybooking.mitra.data.local.db.AppDatabase) = com.booble.eazybooking.mitra.data.local.db.AppDbHelper(appDatabase)

    @Provides
    fun provideAppPreferencesHelper(sharedPref: SharedPreferences) = com.booble.eazybooking.mitra.data.local.pref.AppPreferencesHelper(sharedPref)

    @Provides
    fun providesAppApiHelper(apiService: ApiService) = AppApiHelper(apiService)

    @Provides
    fun providesAppDataManager(appApiHelper: AppApiHelper, appDbHelper: com.booble.eazybooking.mitra.data.local.db.AppDbHelper, appPreferencesHelper: com.booble.eazybooking.mitra.data.local.pref.AppPreferencesHelper): com.booble.eazybooking.mitra.data.AppDataManager {
        return com.booble.eazybooking.mitra.data.AppDataManager(appApiHelper, appDbHelper, appPreferencesHelper)
    }
}