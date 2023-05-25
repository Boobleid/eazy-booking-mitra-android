package com.booble.reservasi.mitra.ui.booking_cancel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.DefaultApiResponse
import com.booble.reservasi.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.reservasi.mitra.data.model.api.booking_cancel.*
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
class BookingCancelViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _bookingCancelList: MutableLiveData<DataResource<BookingCancelListResponse>> = MutableLiveData()
    val bookingCancelList = _bookingCancelList
    fun bookingCancelListApiCall(request: BookingCancelListRequest) = viewModelScope.launch {
        bookingCancelList.value = DataResource.Loading
        bookingCancelList.value = dataRepository.bookingCancelListApiCall(request)
    }

    private val _confirmBookingCancel: MutableLiveData<DataResource<DefaultApiResponse>> = MutableLiveData()
    val confirmBookingCancel = _confirmBookingCancel
    fun confirmBookingCancelApiCall(request: BookingCancelRequest) = viewModelScope.launch {
        confirmBookingCancel.value = DataResource.Loading
        confirmBookingCancel.value = dataRepository.confirmBookingCancelApiCall(request)
    }

    private val _cancelConversation: MutableLiveData<DataResource<CancelConversationResponse>> = MutableLiveData()
    val cancelConversation = _cancelConversation
    fun cancelConversationApiCall(request: BookingCancelRequest) = viewModelScope.launch {
        cancelConversation.value = DataResource.Loading
        cancelConversation.value = dataRepository.bookingCancelConversationApiCall(request)
    }

    private val _readCancelConversation: MutableLiveData<DataResource<DefaultApiResponse>> = MutableLiveData()
    val readCancelConversation = _readCancelConversation
    fun readMessageConversationApiCall(request: BookingCancelRequest) = viewModelScope.launch {
        readCancelConversation.value = DataResource.Loading
        readCancelConversation.value = dataRepository.readMessageConversationApiCall(request)
    }

    private val _sendCancelConversation: MutableLiveData<DataResource<DefaultApiResponse>> = MutableLiveData()
    val sendCancelConversation = _sendCancelConversation
    fun sendMessageConversationApiCall(request: SendMessageConversationRequest) = viewModelScope.launch {
        sendCancelConversation.value = DataResource.Loading
        sendCancelConversation.value = dataRepository.sendMessageConversationApiCall(request)
    }
}