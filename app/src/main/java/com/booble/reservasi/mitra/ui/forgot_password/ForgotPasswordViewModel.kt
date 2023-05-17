package com.booble.reservasi.mitra.ui.forgot_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.model.api.forgot_password.ForgotPasswordRequest
import com.booble.reservasi.mitra.data.model.api.forgot_password.ForgotPasswordResponse
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 07/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _setForgotPassword: MutableLiveData<DataResource<ForgotPasswordResponse>> = MutableLiveData()
    val setForgotPassword: LiveData<DataResource<ForgotPasswordResponse>> = _setForgotPassword

    fun setForgotPasswordApiCall(request: ForgotPasswordRequest) = viewModelScope.launch {
        _setForgotPassword.value = DataResource.Loading
        _setForgotPassword.value = dataRepository.setForgotPasswordApiCall(request)
    }
}