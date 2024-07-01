package com.booble.eazybooking.mitra.base

import com.booble.eazybooking.mitra.utils.UtilConstants.NO_INTERNET
import com.booble.eazybooking.mitra.utils.UtilConstants.OTHER_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Created by rivaldy on 05/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): com.booble.eazybooking.mitra.data.network.DataResource<T> {
        return withContext(Dispatchers.IO) {
            try {
                com.booble.eazybooking.mitra.data.network.DataResource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        com.booble.eazybooking.mitra.data.network.DataResource.Failure(false, throwable.code(), throwable.response()?.errorBody(), throwable.message)
                    }
                    else -> {
                        if (throwable.message == NO_INTERNET) {
                            com.booble.eazybooking.mitra.data.network.DataResource.Failure(true, null, null, throwable.message)
                        } else com.booble.eazybooking.mitra.data.network.DataResource.Failure(true, OTHER_ERROR, null, throwable.message) // CHANGE THIS TO FALSE
                    }
                }
            }
        }
    }
}