package com.booble.reservasi.mitra.back_up.ui.home.detail_order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_detail.BookingDetailRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_detail.BookingDetailResponse
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInRequest
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInResponse
import com.booble.reservasi.mitra.data.network.DataResource
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
* Created by rivaldy on 23/08/21
* Find me on my Github -> https://github.com/im-o
**/

class DetailOrderViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _getBookingUserDetail: MutableLiveData<DataResource<BookingDetailResponse>> = MutableLiveData()
    private val _verificationCheckIn: MutableLiveData<DataResource<VerificationCheckInResponse>> = MutableLiveData()

    val getBookingUserDetail: LiveData<DataResource<BookingDetailResponse>> = _getBookingUserDetail
    val verificationCheckIn: LiveData<DataResource<VerificationCheckInResponse>> = _verificationCheckIn


    fun getBookingUserDetailApiCall(request: BookingDetailRequest) = viewModelScope.launch {
        _getBookingUserDetail.value = DataResource.Loading
        _getBookingUserDetail.value = dataRepository.getBookingUserDetailApiCall(request)
    }

    fun verificationCheckInApiCall(request: VerificationCheckInRequest) = viewModelScope.launch {
        _verificationCheckIn.value = DataResource.Loading
        _verificationCheckIn.value = dataRepository.verificationCheckInApiCall(request)
    }
}