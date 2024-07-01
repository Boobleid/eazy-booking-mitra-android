package com.booble.eazybooking.mitra.ui.service_detail.add_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.eazybooking.mitra.data.DataRepository
import com.booble.eazybooking.mitra.data.model.api.master.facility_building.FacilityBuildingResponse
import com.booble.eazybooking.mitra.data.model.api.master.facility_room.FacilityRoomResponse
import com.booble.eazybooking.mitra.data.model.api.master.furniture.FurnitureResponse
import com.booble.eazybooking.mitra.data.model.api.service.add_room.AddRoomRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_room.AddRoomResponse
import com.booble.eazybooking.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 13/09/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class AddRoomViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _addRoomApiCall: MutableLiveData<DataResource<AddRoomResponse>> = MutableLiveData()
    private val _getFurniture: MutableLiveData<DataResource<FurnitureResponse>> = MutableLiveData()
    private val _getFacilityBuilding: MutableLiveData<DataResource<FacilityBuildingResponse>> = MutableLiveData()
    private val _getFacilityRoom: MutableLiveData<DataResource<FacilityRoomResponse>> = MutableLiveData()

    val addRoom: LiveData<DataResource<AddRoomResponse>> = _addRoomApiCall
    val getFurniture: LiveData<DataResource<FurnitureResponse>> = _getFurniture
    val getFacilityBuilding: LiveData<DataResource<FacilityBuildingResponse>> = _getFacilityBuilding
    val getFacilityRoom: LiveData<DataResource<FacilityRoomResponse>> = _getFacilityRoom

    fun addRoomApiCall(request: AddRoomRequest) = viewModelScope.launch {
        _addRoomApiCall.value = DataResource.Loading
        _addRoomApiCall.value = dataRepository.addRoomApiCall(request)
    }

    fun getFurnitureApiCall() = viewModelScope.launch {
        _getFurniture.value = DataResource.Loading
        _getFurniture.value = dataRepository.getFurnitureApiCall()
    }

    fun getFacilityBuildingApiCall() = viewModelScope.launch {
        _getFacilityBuilding.value = DataResource.Loading
        _getFacilityBuilding.value = dataRepository.getFacilityBuildingApiCall()
    }

    fun getFacilityRoomApiCall() = viewModelScope.launch {
        _getFacilityRoom.value = DataResource.Loading
        _getFacilityRoom.value = dataRepository.getFacilityRoomApiCall()
    }
}