package com.booble.eazybooking.mitra.ui.service_detail.add_room.add_furniture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.eazybooking.mitra.data.DataRepository
import com.booble.eazybooking.mitra.data.model.api.service.add_furniture.AddFurnitureRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_furniture.AddFurnitureResponse
import com.booble.eazybooking.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 16/09/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class AddFurnitureDialogViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _addFurniture: MutableLiveData<DataResource<AddFurnitureResponse>> = MutableLiveData()
    val addFurniture: LiveData<DataResource<AddFurnitureResponse>> = _addFurniture

    fun addFurnitureApiCall(request: AddFurnitureRequest) = viewModelScope.launch {
        _addFurniture.value = DataResource.Loading
        _addFurniture.value = dataRepository.addFurnitureApiCall(request)
    }
}