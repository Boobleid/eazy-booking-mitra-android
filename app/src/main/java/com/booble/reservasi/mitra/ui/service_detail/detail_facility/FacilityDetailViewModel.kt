package com.booble.reservasi.mitra.ui.service_detail.detail_facility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.service.detail_facility.DetailFacilityRequest
import com.booble.reservasi.mitra.data.model.api.service.detail_facility.DetailFacilityResponse
import com.booble.reservasi.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 18/09/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class FacilityDetailViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _getDetailFacility: MutableLiveData<DataResource<DetailFacilityResponse>> = MutableLiveData()
    val getDetailFacility: LiveData<DataResource<DetailFacilityResponse>> = _getDetailFacility

    fun getDetailFacility(request: DetailFacilityRequest) = viewModelScope.launch {
        _getDetailFacility.value = DataResource.Loading
        _getDetailFacility.value = dataRepository.getDetailFacilityApiCall(request)
    }
}