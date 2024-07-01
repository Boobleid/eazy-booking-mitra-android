package com.booble.eazybooking.mitra.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.eazybooking.mitra.data.DataRepository
import com.booble.eazybooking.mitra.data.model.api.login.LoginRequest
import com.booble.eazybooking.mitra.data.model.api.login.LoginResponse
import com.booble.eazybooking.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 21/08/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _loginManual: MutableLiveData<DataResource<LoginResponse>> = MutableLiveData()
    val loginManual: LiveData<DataResource<LoginResponse>> = _loginManual

    fun loginManualApiCall(request: LoginRequest) = viewModelScope.launch {
        _loginManual.value = DataResource.Loading
        _loginManual.value = dataRepository.loginManualApiCall(request)
    }
}