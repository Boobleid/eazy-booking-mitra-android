package com.booble.reservasi.mitra.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.service.ServiceRequest
import com.booble.reservasi.mitra.data.model.api.service.ServiceResponse
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInRequest
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInResponse
import com.booble.reservasi.mitra.data.model.offline.LocationFilter
import com.booble.reservasi.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeOrderViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _getServiceUser: MutableLiveData<DataResource<ServiceResponse>> = MutableLiveData()
    private val _verificationCheckIn: MutableLiveData<DataResource<VerificationCheckInResponse>> = MutableLiveData()
    private val _verificationCheckOut: MutableLiveData<DataResource<VerificationCheckInResponse>> = MutableLiveData()
    val getServiceUser: LiveData<DataResource<ServiceResponse>> = _getServiceUser
    val verificationCheckIn: LiveData<DataResource<VerificationCheckInResponse>> = _verificationCheckIn

    fun getServiceApiCall(request: ServiceRequest) = viewModelScope.launch {
        _getServiceUser.value = DataResource.Loading
        _getServiceUser.value = dataRepository.getServiceApiCall(request)
    }

    fun verificationCheckOutApiCall(request: VerificationCheckInRequest) = viewModelScope.launch {
        _verificationCheckOut.value = DataResource.Loading
        _verificationCheckOut.value = dataRepository.verificationCheckOutApiCall(request)
    }

    // to send data class between fragment
    val locationFilter: MutableLiveData<LocationFilter?> = MutableLiveData()
}