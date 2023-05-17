package com.booble.reservasi.mitra.back_up.ui.home.pop_item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.booking_user.item_condition.ItemConditionRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.item_condition.ItemConditionResponse
import com.booble.reservasi.mitra.data.network.DataResource
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 25/08/21
 * Find me on my Github -> https://github.com/im-o
 **/

class PopUpItemViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _verificationItemCondition: MutableLiveData<DataResource<ItemConditionResponse>> = MutableLiveData()
    val verificationItemCondition: LiveData<DataResource<ItemConditionResponse>> = _verificationItemCondition

    fun verificationItemConditionApiCall(request: ItemConditionRequest) = viewModelScope.launch {
        _verificationItemCondition.value = DataResource.Loading
        _verificationItemCondition.value = dataRepository.verificationItemConditionApiCall(request)
    }
}