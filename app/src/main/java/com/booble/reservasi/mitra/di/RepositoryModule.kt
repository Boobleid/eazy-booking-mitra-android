package com.booble.reservasi.mitra.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by rivaldy on 01/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideDataRepository(appDataManager: com.booble.reservasi.mitra.data.AppDataManager): com.booble.reservasi.mitra.data.DataRepository {
        return com.booble.reservasi.mitra.data.DataRepository(appDataManager)
    }
}