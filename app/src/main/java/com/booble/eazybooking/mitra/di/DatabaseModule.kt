package com.booble.eazybooking.mitra.di

import android.content.Context
import androidx.room.Room
import com.booble.eazybooking.mitra.utils.UtilConstants.DB_THE_MOVIE_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by rivaldy on 01/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): com.booble.eazybooking.mitra.data.local.db.AppDatabase {
        return Room.databaseBuilder(context, com.booble.eazybooking.mitra.data.local.db.AppDatabase::class.java, DB_THE_MOVIE_DB).build()
    }
}