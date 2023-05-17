package com.booble.reservasi.mitra.ui.withdraw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.DefaultApiResponse
import com.booble.reservasi.mitra.data.model.api.withdraw.WithdrawRequest
import com.booble.reservasi.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 21/09/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _getWithdraw: MutableLiveData<DataResource<DefaultApiResponse>> = MutableLiveData()
    val getWithdraw: LiveData<DataResource<DefaultApiResponse>> = _getWithdraw

    fun getWithdrawApiCall(request: WithdrawRequest) = viewModelScope.launch {
        _getWithdraw.value = DataResource.Loading
        _getWithdraw.value = dataRepository.getWithdrawApiCall(request)
    }
}