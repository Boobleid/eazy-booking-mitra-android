package com.booble.reservasi.mitra.back_up.ui.home.check_item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_item.BookingItemRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_item.BookingItemResponse
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInRequest
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInResponse
import com.booble.reservasi.mitra.data.network.DataResource
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 24/08/21
 * Find me on my Github -> https://github.com/im-o
 **/

class CheckItemViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _getItemBooking: MutableLiveData<DataResource<BookingItemResponse>> = MutableLiveData()
    private val _verificationCheckOut: MutableLiveData<DataResource<VerificationCheckInResponse>> = MutableLiveData()

    val getItemBookingApiCall: LiveData<DataResource<BookingItemResponse>> = _getItemBooking
    val verificationCheckOut: LiveData<DataResource<VerificationCheckInResponse>> = _verificationCheckOut

    fun getItemBookingApiCall(request: BookingItemRequest) = viewModelScope.launch {
        _getItemBooking.value = DataResource.Loading
        _getItemBooking.value = dataRepository.getItemBookingApiCall(request)
    }

    fun verificationCheckOutApiCall(request: VerificationCheckInRequest) = viewModelScope.launch {
        _verificationCheckOut.value = DataResource.Loading
        _verificationCheckOut.value = dataRepository.verificationCheckOutApiCall(request)
    }
}