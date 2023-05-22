package com.booble.reservasi.mitra.ui.booking_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.reservasi.mitra.data.model.api.calendar.BookingDateCalendarRequest
import com.booble.reservasi.mitra.data.model.api.calendar.BookingDateCalendarResponse
import com.booble.reservasi.mitra.data.model.api.check_history.CheckOutHistoryResponse
import com.booble.reservasi.mitra.data.model.api.facility.FacilityListRequest
import com.booble.reservasi.mitra.data.model.api.facility.FacilityListResponse
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

    private val _bookingDate: MutableLiveData<DataResource<BookingDateCalendarResponse>> = MutableLiveData()
    val bookingDate = _bookingDate
    fun loadBookingDateApiCall(request: BookingDateCalendarRequest) = viewModelScope.launch {
        _bookingDate.value = DataResource.Loading
        _bookingDate.value = dataRepository.loadBookingDate(request)
    }

    private val _facilityList: MutableLiveData<DataResource<FacilityListResponse>> = MutableLiveData()
    val facilityList = _facilityList
    fun facilityListApiCall(request: FacilityListRequest) = viewModelScope.launch {
        _facilityList.value = DataResource.Loading
        _facilityList.value = dataRepository.facilityListApiCall(request)
    }
}