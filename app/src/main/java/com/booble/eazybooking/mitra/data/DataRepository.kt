package com.booble.eazybooking.mitra.data

import com.booble.eazybooking.mitra.base.BaseRepository
import com.booble.eazybooking.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.eazybooking.mitra.data.model.api.add_member.AddMemberServiceRequest
import com.booble.eazybooking.mitra.data.model.api.add_member.DeleteMemberServiceRequest
import com.booble.eazybooking.mitra.data.model.api.booking_cancel.BookingCancelListRequest
import com.booble.eazybooking.mitra.data.model.api.booking_cancel.BookingCancelRequest
import com.booble.eazybooking.mitra.data.model.api.booking_cancel.SendMessageConversationRequest
import com.booble.eazybooking.mitra.data.model.api.booking_user.BookingUserRequest
import com.booble.eazybooking.mitra.data.model.api.booking_user.booking_detail.BookingDetailRequest
import com.booble.eazybooking.mitra.data.model.api.booking_user.booking_item.BookingItemRequest
import com.booble.eazybooking.mitra.data.model.api.booking_user.item_condition.ItemConditionRequest
import com.booble.eazybooking.mitra.data.model.api.calendar.BookingDateCalendarRequest
import com.booble.eazybooking.mitra.data.model.api.check_history.detail.DetailCheckOutHistoryRequest
import com.booble.eazybooking.mitra.data.model.api.facility.FacilityListRequest
import com.booble.eazybooking.mitra.data.model.api.forgot_password.ForgotPasswordRequest
import com.booble.eazybooking.mitra.data.model.api.help.HelpRequest
import com.booble.eazybooking.mitra.data.model.api.login.LoginRequest
import com.booble.eazybooking.mitra.data.model.api.room_facility.RoomFacilityRequest
import com.booble.eazybooking.mitra.data.model.api.room_facility.status_display.StatusDisplayRoomFacilityRequest
import com.booble.eazybooking.mitra.data.model.api.service.ServiceRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_facility.AddFacilityRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_furniture.AddFurnitureRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_room.AddRoomRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_service.AddServiceRequest
import com.booble.eazybooking.mitra.data.model.api.service.detail_facility.DetailFacilityRequest
import com.booble.eazybooking.mitra.data.model.api.service.detail_room.DetailRoomRequest
import com.booble.eazybooking.mitra.data.model.api.user_profile.UserProfileRequest
import com.booble.eazybooking.mitra.data.model.api.user_profile.update_profile.UpdateProfileRequest
import com.booble.eazybooking.mitra.data.model.api.verfication_check_in.VerificationCheckInRequest
import com.booble.eazybooking.mitra.data.model.api.withdraw.WithdrawRequest
import com.booble.eazybooking.mitra.data.model.db.movie.MovieEntity
import javax.inject.Inject

/**
 * Created by rivaldy on 03/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class DataRepository @Inject constructor(
    private val appDataManager: AppDataManager
) : BaseRepository() {

    /** Remote Data - Fetch API **/
    suspend fun getMoviesApiCall() = safeApiCall {
        appDataManager.getMoviesApiCall()
    }

    suspend fun loginManualApiCall(request: LoginRequest) = safeApiCall {
        appDataManager.loginManualApiCall(request)
    }

    suspend fun setForgotPasswordApiCall(request: ForgotPasswordRequest) = safeApiCall {
        appDataManager.setForgotPasswordApiCall(request)
    }

    suspend fun getBookingUserApiCall(request: BookingUserRequest) = safeApiCall {
        appDataManager.getBookingUserApiCall(getAccessToken(), request)
    }

    suspend fun getBookingUserDetailApiCall(request: BookingDetailRequest) = safeApiCall {
        appDataManager.getBookingUserDetailApiCall(getAccessToken(), request)
    }

    suspend fun verificationCheckInApiCall(request: VerificationCheckInRequest) = safeApiCall {
        appDataManager.verificationCheckInApiCall(getAccessToken(), request)
    }

    suspend fun verificationCheckOutApiCall(request: VerificationCheckInRequest) = safeApiCall {
        appDataManager.verificationCheckOutApiCall(getAccessToken(), request)
    }

    suspend fun verificationItemConditionApiCall(request: ItemConditionRequest) = safeApiCall {
        appDataManager.verificationItemConditionApiCall(getAccessToken(), request)
    }

    suspend fun getItemBookingApiCall(request: BookingItemRequest) = safeApiCall {
        appDataManager.getItemBookingApiCall(getAccessToken(), request)
    }

    suspend fun getCheckOutHistoryApiCall(request: DefaultLimitOffsetRequest) = safeApiCall {
        appDataManager.getCheckOutHistoryApiCall(getAccessToken(), request)
    }

    suspend fun getDetailCheckOutHistoryApiCall(request: DetailCheckOutHistoryRequest) = safeApiCall {
        appDataManager.getDetailCheckOutHistoryApiCall(getAccessToken(), request)
    }

    suspend fun getUserProfileApiCall() = safeApiCall {
        appDataManager.getUserProfileApiCall(getAccessToken(), UserProfileRequest(getCurrentUserId()))
    }

    suspend fun setUpdateProfileApiCall(request: UpdateProfileRequest) = safeApiCall {
        appDataManager.setUpdateProfileApiCall(getAccessToken(), request)
    }

    // NEW API

    suspend fun getServiceApiCall(request: ServiceRequest) = safeApiCall {
        appDataManager.getServiceApiCall(getAccessToken(), request)
    }

    suspend fun getCityApiCall() = safeApiCall {
        appDataManager.getCityApiCall(getAccessToken())
    }

    suspend fun getServiceCategoryApiCall() = safeApiCall {
        appDataManager.getServiceCategoryApiCall(getAccessToken())
    }

    suspend fun addServiceApiCall(request: AddServiceRequest) = safeApiCall {
        appDataManager.addServiceApiCall(getAccessToken(), request)
    }

    suspend fun addFurnitureApiCall(request: AddFurnitureRequest) = safeApiCall {
        appDataManager.addFurnitureApiCall(getAccessToken(), request)
    }

    suspend fun addRoomApiCall(request: AddRoomRequest) = safeApiCall {
        appDataManager.addRoomApiCall(getAccessToken(), request)
    }

    suspend fun facilityListApiCall(request: FacilityListRequest) = safeApiCall {
        appDataManager.facilityListApiCall(getAccessToken(), request)
    }

    suspend fun addFacilityApiCall(request: AddFacilityRequest) = safeApiCall {
        appDataManager.addFacilityApiCall(getAccessToken(), request)
    }

    suspend fun getFurnitureApiCall() = safeApiCall {
        appDataManager.getFurnitureApiCall(getAccessToken())
    }

    suspend fun getFacilityBuildingApiCall() = safeApiCall {
        appDataManager.getFacilityBuildingApiCall(getAccessToken())
    }

    suspend fun getFacilityRoomApiCall() = safeApiCall {
        appDataManager.getFacilityRoomApiCall(getAccessToken())
    }

    suspend fun getDetailRoomApiCall(request: DetailRoomRequest) = safeApiCall {
        appDataManager.getDetailRoomApiCall(getAccessToken(), request)
    }

    suspend fun getDetailFacilityApiCall(request: DetailFacilityRequest) = safeApiCall {
        appDataManager.getDetailFacilityApiCall(getAccessToken(), request)
    }

    suspend fun getRoomFacilityApiCall(request: RoomFacilityRequest) = safeApiCall {
        appDataManager.getRoomFacilityApiCall(getAccessToken(), request)
    }

    suspend fun setStatusDisplayApiCall(request: StatusDisplayRoomFacilityRequest) = safeApiCall {
        appDataManager.setStatusDisplayApiCall(getAccessToken(), request)
    }

    suspend fun addMemberServiceApiCall(request: AddMemberServiceRequest) = safeApiCall {
        appDataManager.addMemberServiceApiCall(getAccessToken(), request)
    }

    suspend fun getWithdrawApiCall(request: WithdrawRequest) = safeApiCall {
        appDataManager.getWithdrawApiCall(getAccessToken(), request)
    }

    suspend fun getMemberServiceApiCall() = safeApiCall {
        appDataManager.getMemberServiceApiCall(getAccessToken())
    }

    suspend fun deleteMemberServiceApiCall(request: DeleteMemberServiceRequest) = safeApiCall {
        appDataManager.deleteMemberServiceApiCall(getAccessToken(), request)
    }

    suspend fun getBalanceHistoryApiCall() = safeApiCall {
        appDataManager.getBalanceHistoryApiCall(getAccessToken())
    }

    suspend fun loadListHelpApiCall(request: HelpRequest) = safeApiCall {
        appDataManager.loadListHelpApiCall(getAccessToken(), request)
    }

    suspend fun loadListContactApiCall() = safeApiCall {
        appDataManager.loadListContactApiCall(getAccessToken())
    }

    suspend fun loadBookingDate(request: BookingDateCalendarRequest) = safeApiCall {
        appDataManager.loadBookingDate(getAccessToken(), request)
    }

    suspend fun bookingCancelListApiCall(request: BookingCancelListRequest) = safeApiCall {
        appDataManager.bookingCancelListApiCall(getAccessToken(), request)
    }

    suspend fun confirmBookingCancelApiCall(request: BookingCancelRequest) = safeApiCall {
        appDataManager.confirmBookingCancelApiCall(getAccessToken(), request)
    }

    suspend fun numberBookingCancelApiCall() = safeApiCall {
        appDataManager.numberBookingCancelApiCall(getAccessToken())
    }

    suspend fun bookingCancelConversationApiCall(request: BookingCancelRequest) = safeApiCall {
        appDataManager.bookingCancelConversationApiCall(getAccessToken(), request)
    }

    suspend fun readMessageConversationApiCall(request: BookingCancelRequest) = safeApiCall {
        appDataManager.readMessageConversationApiCall(getAccessToken(), request)
    }

    suspend fun sendMessageConversationApiCall(request: SendMessageConversationRequest) = safeApiCall {
        appDataManager.sendMessageConversationApiCall(getAccessToken(), request)
    }

    /** Local Data - Room Local Storage **/
    fun getMoviesLocal() = appDataManager.getAllMovie()

    suspend fun insertMoviesLocal(movies: MutableList<MovieEntity>) = appDataManager.insertAllMovie(movies)

    suspend fun clearMovies() = appDataManager.clearMovies()

    /** Local Data - SharedPreference Storage **/
    fun getUrlAPI() = appDataManager.getUrlAPI()
    fun setUrlAPI(url: String) {
        appDataManager.setUrlAPI(url)
    }

    fun setAccessToken(token: String) {
        appDataManager.setAccessToken(token)
    }

    fun getAccessToken(): String {
        return appDataManager.getAccessToken()
    }

    fun setCurrentUserId(id: String) {
        appDataManager.setCurrentUserId(id)
    }

    fun getCurrentUserId(): String {
        return appDataManager.getCurrentUserId()
    }
}
