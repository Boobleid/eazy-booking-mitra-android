package com.booble.reservasi.mitra.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.user_profile.update_profile.UpdateProfileRequest
import com.booble.reservasi.mitra.data.model.api.user_profile.update_profile.UpdateProfileResponse
import com.booble.reservasi.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 26/08/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class UserSettingViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _setUpdateProfile: MutableLiveData<DataResource<UpdateProfileResponse>> = MutableLiveData()
    val setUpdateProfile: LiveData<DataResource<UpdateProfileResponse>> = _setUpdateProfile

    fun setUpdateProfileApiCall(request: UpdateProfileRequest) = viewModelScope.launch {
        _setUpdateProfile.value = DataResource.Loading
        _setUpdateProfile.value = dataRepository.setUpdateProfileApiCall(request)
    }
}