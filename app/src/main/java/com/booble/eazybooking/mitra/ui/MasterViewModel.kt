package com.booble.eazybooking.mitra.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booble.eazybooking.mitra.data.DataRepository
import com.booble.eazybooking.mitra.data.model.api.balance_history.BalanceHistoryResponse
import com.booble.eazybooking.mitra.data.model.api.booking_cancel.NumberBookingCancelResponse
import com.booble.eazybooking.mitra.data.model.api.help.HelpRequest
import com.booble.eazybooking.mitra.data.model.api.help.HelpResponse
import com.booble.eazybooking.mitra.data.model.api.help.contact.ContactResponse
import com.booble.eazybooking.mitra.data.model.api.master.category.CategoryResponse
import com.booble.eazybooking.mitra.data.model.api.master.city.CityResponse
import com.booble.eazybooking.mitra.data.model.api.user_profile.UserProfileResponse
import com.booble.eazybooking.mitra.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by rivaldy on 10/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

@HiltViewModel
class MasterViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _getUserProfile: MutableLiveData<DataResource<UserProfileResponse>> = MutableLiveData()
    private val _getCity: MutableLiveData<DataResource<CityResponse>> = MutableLiveData()
    private val _getServiceCategory: MutableLiveData<DataResource<CategoryResponse>> = MutableLiveData()
    private val _getBalanceHistory: MutableLiveData<DataResource<BalanceHistoryResponse>> = MutableLiveData()
    private val _loadListHelp: MutableLiveData<DataResource<HelpResponse>> = MutableLiveData()
    private val _loadListContact: MutableLiveData<DataResource<ContactResponse>> = MutableLiveData()

    val getUserProfile: LiveData<DataResource<UserProfileResponse>> = _getUserProfile
    val getCity: LiveData<DataResource<CityResponse>> = _getCity
    val getServiceCategory: LiveData<DataResource<CategoryResponse>> = _getServiceCategory
    val getBalanceHistory: LiveData<DataResource<BalanceHistoryResponse>> = _getBalanceHistory
    val loadListHelp: LiveData<DataResource<HelpResponse>> = _loadListHelp
    val loadListContact: LiveData<DataResource<ContactResponse>> = _loadListContact

    fun getUserProfileApiCall() = viewModelScope.launch {
        _getUserProfile.value = DataResource.Loading
        _getUserProfile.value = dataRepository.getUserProfileApiCall()
    }

    fun getCityApiCall() = viewModelScope.launch {
        _getCity.value = DataResource.Loading
        _getCity.value = dataRepository.getCityApiCall()
    }

    fun getServiceCategoryApiCall() = viewModelScope.launch {
        _getServiceCategory.value = DataResource.Loading
        _getServiceCategory.value = dataRepository.getServiceCategoryApiCall()
    }

    fun getBalanceHistoryApiCall() = viewModelScope.launch {
        _getBalanceHistory.value = DataResource.Loading
        _getBalanceHistory.value = dataRepository.getBalanceHistoryApiCall()
    }

    fun loadListHelpApiCall(request: HelpRequest) = viewModelScope.launch {
        _loadListHelp.value = DataResource.Loading
        _loadListHelp.value = dataRepository.loadListHelpApiCall(request)
    }

    fun loadListContactApiCall() = viewModelScope.launch {
        _loadListContact.value = DataResource.Loading
        _loadListContact.value = dataRepository.loadListContactApiCall()
    }

    private val _numberBookingCancel: MutableLiveData<DataResource<NumberBookingCancelResponse>> = MutableLiveData()
    val numberBookingCancel = _numberBookingCancel
    fun numberBookingCancelApiCall() = viewModelScope.launch {
        _numberBookingCancel.value = DataResource.Loading
        _numberBookingCancel.value = dataRepository.numberBookingCancelApiCall()
    }

    fun getUrlAPI() = dataRepository.getUrlAPI()
    fun setUrlAPI(token: String) {
        dataRepository.setUrlAPI(token)
    }

    fun setAccessToken(token: String) {
        dataRepository.setAccessToken(token)
    }

    fun getAccessToken(): String {
        return dataRepository.getAccessToken()
    }

    fun setCurrentUserId(id: String) {
        dataRepository.setCurrentUserId(id)
    }

    fun getCurrentUserId(): String {
        return dataRepository.getCurrentUserId()
    }

    fun resetUserLogin() {
        setAccessToken("")
        setCurrentUserId("")
    }
}