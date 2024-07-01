package com.booble.eazybooking.mitra.ui.service_detail.add_facility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.eazybooking.mitra.data.DataRepository
import com.booble.eazybooking.mitra.data.model.api.service.add_facility.AddFacilityRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_facility.AddFacilityResponse
import com.booble.eazybooking.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 20/09/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class AddFacilityViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _addFacilityApiCall: MutableLiveData<DataResource<AddFacilityResponse>> = MutableLiveData()
    val addFacility: LiveData<DataResource<AddFacilityResponse>> = _addFacilityApiCall

    fun addFacilityApiCall(request: AddFacilityRequest) = viewModelScope.launch {
        _addFacilityApiCall.value = DataResource.Loading
        _addFacilityApiCall.value = dataRepository.addFacilityApiCall(request)
    }
}