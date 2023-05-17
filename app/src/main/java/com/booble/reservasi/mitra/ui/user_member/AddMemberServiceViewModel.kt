package com.booble.reservasi.mitra.ui.user_member

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.reservasi.mitra.data.DataRepository
import com.booble.reservasi.mitra.data.model.api.DefaultApiResponse
import com.booble.reservasi.mitra.data.model.api.add_member.AddMemberServiceRequest
import com.booble.reservasi.mitra.data.model.api.add_member.DeleteMemberServiceRequest
import com.booble.reservasi.mitra.data.model.api.add_member.MemberServiceResponse
import com.booble.reservasi.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 21/09/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class AddMemberServiceViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _addMemberService: MutableLiveData<DataResource<DefaultApiResponse>> = MutableLiveData()
    private val _getMemberService: MutableLiveData<DataResource<MemberServiceResponse>> = MutableLiveData()
    private val _deleteMemberService: MutableLiveData<DataResource<DefaultApiResponse>> = MutableLiveData()
    val addMemberService: LiveData<DataResource<DefaultApiResponse>> = _addMemberService
    val getMemberService: LiveData<DataResource<MemberServiceResponse>> = _getMemberService
    val deleteMemberService: LiveData<DataResource<DefaultApiResponse>> = _deleteMemberService

    fun addMemberServiceApiCall(request: AddMemberServiceRequest) = viewModelScope.launch {
        _addMemberService.value = DataResource.Loading
        _addMemberService.value = dataRepository.addMemberServiceApiCall(request)
    }

    fun getMemberServiceApiCall() = viewModelScope.launch {
        _getMemberService.value = DataResource.Loading
        _getMemberService.value = dataRepository.getMemberServiceApiCall()
    }

    fun deleteMemberServiceApiCall(request: DeleteMemberServiceRequest) = viewModelScope.launch {
        _deleteMemberService.value = DataResource.Loading
        _deleteMemberService.value = dataRepository.deleteMemberServiceApiCall(request)
    }
}