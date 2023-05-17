package com.booble.reservasi.mitra.ui.service_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.DefaultApiResponse
import com.booble.reservasi.mitra.data.model.api.room_facility.RoomFacilityRequest
import com.booble.reservasi.mitra.data.model.api.room_facility.RoomFacilityResponse
import com.booble.reservasi.mitra.data.model.api.room_facility.status_display.StatusDisplayRoomFacilityRequest
import com.booble.reservasi.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 17/09/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class ServiceDetailViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    private val _getRoomFacility: MutableLiveData<DataResource<RoomFacilityResponse>> = MutableLiveData()
    val getRoomFacility: LiveData<DataResource<RoomFacilityResponse>> = _getRoomFacility

    fun getRoomFacilityApiCall(request: RoomFacilityRequest) = viewModelScope.launch {
        _getRoomFacility.value = DataResource.Loading
        _getRoomFacility.value = dataRepository.getRoomFacilityApiCall(request)
    }

    private val _statusDisplay: MutableLiveData<DataResource<DefaultApiResponse>> = MutableLiveData()
    val statusDisplay: LiveData<DataResource<DefaultApiResponse>> = _statusDisplay

    fun setStatusDisplayApiCall(request: StatusDisplayRoomFacilityRequest) = viewModelScope.launch {
        _statusDisplay.value = DataResource.Loading
        _statusDisplay.value = dataRepository.setStatusDisplayApiCall(request)
    }
}