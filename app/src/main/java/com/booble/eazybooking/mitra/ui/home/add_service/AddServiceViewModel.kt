package com.booble.eazybooking.mitra.ui.home.add_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.eazybooking.mitra.data.DataRepository
import com.booble.eazybooking.mitra.data.model.api.service.add_furniture.AddFurnitureRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_furniture.AddFurnitureResponse
import com.booble.eazybooking.mitra.data.model.api.service.add_service.AddServiceRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_service.AddServiceResponse
import com.booble.eazybooking.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 10/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class AddServiceViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _addService: MutableLiveData<DataResource<AddServiceResponse>> = MutableLiveData()
    private val _addFurniture: MutableLiveData<DataResource<AddFurnitureResponse>> = MutableLiveData()
    val addService: LiveData<DataResource<AddServiceResponse>> = _addService
    val addFurniture: LiveData<DataResource<AddFurnitureResponse>> = _addFurniture

    fun addServiceApiCall(request: AddServiceRequest) = viewModelScope.launch {
        _addService.value = DataResource.Loading
        _addService.value = dataRepository.addServiceApiCall(request)
    }

    fun addFurnitureApiCall(request: AddFurnitureRequest) = viewModelScope.launch {
        _addFurniture.value = DataResource.Loading
        _addFurniture.value = dataRepository.addFurnitureApiCall(request)
    }
}