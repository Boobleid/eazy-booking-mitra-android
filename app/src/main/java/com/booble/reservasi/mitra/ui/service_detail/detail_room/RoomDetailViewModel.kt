package com.booble.reservasi.mitra.ui.service_detail.detail_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.service.detail_room.DetailRoomRequest
import com.booble.reservasi.mitra.data.model.api.service.detail_room.DetailRoomResponse
import com.booble.reservasi.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 18/09/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class RoomDetailViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _getDetailRoom: MutableLiveData<DataResource<DetailRoomResponse>> = MutableLiveData()
    val getDetailRoom: LiveData<DataResource<DetailRoomResponse>> = _getDetailRoom

    fun getDetailRoomApiCall(request: DetailRoomRequest) = viewModelScope.launch {
        _getDetailRoom.value = DataResource.Loading
        _getDetailRoom.value = dataRepository.getDetailRoomApiCall(request)
    }
}