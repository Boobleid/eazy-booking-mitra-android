package com.booble.reservasi.mitra.ui.booking_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.reservasi.mitra.data.model.api.check_history.CheckOutHistoryResponse
import com.booble.reservasi.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 26/08/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class BookingHistoryViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _getCheckOutHistory: MutableLiveData<DataResource<CheckOutHistoryResponse>> = MutableLiveData()
    val getCheckOutHistory: LiveData<DataResource<CheckOutHistoryResponse>> = _getCheckOutHistory

    fun getCheckOutHistoryApiCall(request: DefaultLimitOffsetRequest) = viewModelScope.launch {
        _getCheckOutHistory.value = DataResource.Loading
        _getCheckOutHistory.value = dataRepository.getCheckOutHistoryApiCall(request)
    }
}