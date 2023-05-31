package com.booble.reservasi.mitra.data.remote

import com.booble.reservasi.mitra.data.model.api.DefaultApiResponse
import com.booble.reservasi.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.reservasi.mitra.data.model.api.add_member.AddMemberServiceRequest
import com.booble.reservasi.mitra.data.model.api.add_member.DeleteMemberServiceRequest
import com.booble.reservasi.mitra.data.model.api.add_member.MemberServiceResponse
import com.booble.reservasi.mitra.data.model.api.balance_history.BalanceHistoryResponse
import com.booble.reservasi.mitra.data.model.api.booking_cancel.BookingCancelListRequest
import com.booble.reservasi.mitra.data.model.api.booking_cancel.BookingCancelListResponse
import com.booble.reservasi.mitra.data.model.api.booking_cancel.BookingCancelRequest
import com.booble.reservasi.mitra.data.model.api.booking_cancel.SendMessageConversationRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.BookingUserRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.BookingUserResponse
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_detail.BookingDetailRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_detail.BookingDetailResponse
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_item.BookingItemRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.booking_item.BookingItemResponse
import com.booble.reservasi.mitra.data.model.api.booking_user.item_condition.ItemConditionRequest
import com.booble.reservasi.mitra.data.model.api.booking_user.item_condition.ItemConditionResponse
import com.booble.reservasi.mitra.data.model.api.calendar.BookingDateCalendarRequest
import com.booble.reservasi.mitra.data.model.api.calendar.BookingDateCalendarResponse
import com.booble.reservasi.mitra.data.model.api.check_history.CheckOutHistoryResponse
import com.booble.reservasi.mitra.data.model.api.check_history.detail.DetailCheckOutHistoryRequest
import com.booble.reservasi.mitra.data.model.api.check_history.detail.DetailCheckOutHistoryResponse
import com.booble.reservasi.mitra.data.model.api.facility.FacilityListRequest
import com.booble.reservasi.mitra.data.model.api.facility.FacilityListResponse
import com.booble.reservasi.mitra.data.model.api.forgot_password.ForgotPasswordRequest
import com.booble.reservasi.mitra.data.model.api.help.HelpRequest
import com.booble.reservasi.mitra.data.model.api.help.HelpResponse
import com.booble.reservasi.mitra.data.model.api.help.contact.ContactResponse
import com.booble.reservasi.mitra.data.model.api.login.LoginRequest
import com.booble.reservasi.mitra.data.model.api.master.category.CategoryResponse
import com.booble.reservasi.mitra.data.model.api.master.city.CityResponse
import com.booble.reservasi.mitra.data.model.api.master.facility_building.FacilityBuildingResponse
import com.booble.reservasi.mitra.data.model.api.master.facility_room.FacilityRoomResponse
import com.booble.reservasi.mitra.data.model.api.master.furniture.FurnitureResponse
import com.booble.reservasi.mitra.data.model.api.room_facility.RoomFacilityRequest
import com.booble.reservasi.mitra.data.model.api.room_facility.RoomFacilityResponse
import com.booble.reservasi.mitra.data.model.api.room_facility.status_display.StatusDisplayRoomFacilityRequest
import com.booble.reservasi.mitra.data.model.api.service.ServiceRequest
import com.booble.reservasi.mitra.data.model.api.service.ServiceResponse
import com.booble.reservasi.mitra.data.model.api.service.add_facility.AddFacilityRequest
import com.booble.reservasi.mitra.data.model.api.service.add_facility.AddFacilityResponse
import com.booble.reservasi.mitra.data.model.api.service.add_furniture.AddFurnitureRequest
import com.booble.reservasi.mitra.data.model.api.service.add_furniture.AddFurnitureResponse
import com.booble.reservasi.mitra.data.model.api.service.add_room.AddRoomRequest
import com.booble.reservasi.mitra.data.model.api.service.add_room.AddRoomResponse
import com.booble.reservasi.mitra.data.model.api.service.add_service.AddServiceRequest
import com.booble.reservasi.mitra.data.model.api.service.add_service.AddServiceResponse
import com.booble.reservasi.mitra.data.model.api.service.detail_facility.DetailFacilityRequest
import com.booble.reservasi.mitra.data.model.api.service.detail_facility.DetailFacilityResponse
import com.booble.reservasi.mitra.data.model.api.service.detail_room.DetailRoomRequest
import com.booble.reservasi.mitra.data.model.api.service.detail_room.DetailRoomResponse
import com.booble.reservasi.mitra.data.model.api.user_profile.UserProfileRequest
import com.booble.reservasi.mitra.data.model.api.user_profile.UserProfileResponse
import com.booble.reservasi.mitra.data.model.api.user_profile.update_profile.UpdateProfileRequest
import com.booble.reservasi.mitra.data.model.api.user_profile.update_profile.UpdateProfileResponse
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInRequest
import com.booble.reservasi.mitra.data.model.api.verfication_check_in.VerificationCheckInResponse
import com.booble.reservasi.mitra.data.model.api.withdraw.WithdrawRequest
import javax.inject.Inject

/**
 * Created by rivaldy on 07/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class AppApiHelper @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {

    override suspend fun getMoviesApiCall() = apiService.getMovies()
    override suspend fun loginManualApiCall(request: LoginRequest) = apiService.loginManual(request)
    override suspend fun setForgotPasswordApiCall(request: ForgotPasswordRequest) =
        apiService.setForgotPassword(request)

    override suspend fun getBookingUserApiCall(
        token: String,
        request: BookingUserRequest
    ): BookingUserResponse {
        return apiService.getBookingUser(token, request)
    }

    override suspend fun getBookingUserDetailApiCall(
        token: String,
        request: BookingDetailRequest
    ): BookingDetailResponse {
        return apiService.getBookingUserDetail(token, request)
    }

    override suspend fun verificationCheckInApiCall(
        token: String,
        request: VerificationCheckInRequest
    ): VerificationCheckInResponse {
        return apiService.verificationCheckIn(token, request)
    }

    override suspend fun verificationCheckOutApiCall(
        token: String,
        request: VerificationCheckInRequest
    ): VerificationCheckInResponse {
        return apiService.verificationCheckOut(token, request)
    }

    override suspend fun verificationItemConditionApiCall(
        token: String,
        request: ItemConditionRequest
    ): ItemConditionResponse {
        return apiService.verificationItemCondition(token, request)
    }

    override suspend fun getItemBookingApiCall(
        token: String,
        request: BookingItemRequest
    ): BookingItemResponse {
        return apiService.getItemBooking(token, request)
    }

    override suspend fun getCheckOutHistoryApiCall(
        token: String,
        request: DefaultLimitOffsetRequest
    ): CheckOutHistoryResponse {
        return apiService.getCheckOutHistory(token, request)
    }

    override suspend fun getDetailCheckOutHistoryApiCall(
        token: String,
        request: DetailCheckOutHistoryRequest
    ): DetailCheckOutHistoryResponse {
        return apiService.getDetailCheckOutHistory(token, request)
    }

    override suspend fun getUserProfileApiCall(
        token: String,
        request: UserProfileRequest
    ): UserProfileResponse {
        return apiService.getUserProfile(token, request)
    }

    override suspend fun setUpdateProfileApiCall(
        token: String,
        request: UpdateProfileRequest
    ): UpdateProfileResponse {
        return apiService.setUpdateProfile(token, request)
    }

    override suspend fun getServiceApiCall(
        token: String,
        request: ServiceRequest
    ): ServiceResponse {
        return apiService.getService(token, request)
    }

    override suspend fun getCityApiCall(token: String): CityResponse {
        return apiService.getCity(token)
    }

    override suspend fun getServiceCategoryApiCall(token: String): CategoryResponse {
        return apiService.getServiceCategory(token)
    }

    override suspend fun addServiceApiCall(
        token: String,
        request: AddServiceRequest
    ): AddServiceResponse {
        return apiService.addService(token, request)
    }

    override suspend fun addFurnitureApiCall(
        token: String,
        request: AddFurnitureRequest
    ): AddFurnitureResponse {
        return apiService.addFurniture(token, request)
    }

    override suspend fun addRoomApiCall(token: String, request: AddRoomRequest): AddRoomResponse {
        return apiService.addRoom(token, request)
    }

    override suspend fun facilityListApiCall(
        token: String,
        request: FacilityListRequest
    ): FacilityListResponse {
        return apiService.facilityList(token, request)
    }

    override suspend fun addFacilityApiCall(
        token: String,
        request: AddFacilityRequest
    ): AddFacilityResponse {
        return apiService.addFacility(token, request)
    }

    override suspend fun getFurnitureApiCall(token: String): FurnitureResponse {
        return apiService.getFurniture(token)
    }

    override suspend fun getFacilityBuildingApiCall(token: String): FacilityBuildingResponse {
        return apiService.getFacilityBuilding(token)
    }

    override suspend fun getFacilityRoomApiCall(token: String): FacilityRoomResponse {
        return apiService.getFacilityRoom(token)
    }

    override suspend fun getDetailRoomApiCall(
        token: String,
        request: DetailRoomRequest
    ): DetailRoomResponse {
        return apiService.getDetailRoom(token, request)
    }

    override suspend fun getDetailFacilityApiCall(
        token: String,
        request: DetailFacilityRequest
    ): DetailFacilityResponse {
        return apiService.getDetailFacility(token, request)
    }

    override suspend fun getRoomFacilityApiCall(
        token: String,
        request: RoomFacilityRequest
    ): RoomFacilityResponse {
        return apiService.getRoomFacility(token, request)
    }

    override suspend fun setStatusDisplayApiCall(
        token: String,
        request: StatusDisplayRoomFacilityRequest
    ): DefaultApiResponse {
        return apiService.setUpdateStatusDisplay(token, request)
    }

    override suspend fun addMemberServiceApiCall(
        token: String,
        request: AddMemberServiceRequest
    ): DefaultApiResponse {
        return apiService.addMemberService(token, request)
    }

    override suspend fun getWithdrawApiCall(
        token: String,
        request: WithdrawRequest
    ): DefaultApiResponse {
        return apiService.getWithdraw(token, request)
    }

    override suspend fun getMemberServiceApiCall(token: String): MemberServiceResponse {
        return apiService.getMemberService(token)
    }

    override suspend fun deleteMemberServiceApiCall(
        token: String,
        request: DeleteMemberServiceRequest
    ): DefaultApiResponse {
        return apiService.deleteMemberService(token, request)
    }

    override suspend fun getBalanceHistoryApiCall(token: String): BalanceHistoryResponse {
        return apiService.getBalanceHistory(token)
    }

    override suspend fun loadListHelpApiCall(token: String, request: HelpRequest): HelpResponse {
        return apiService.loadListHelp(token, request)
    }

    override suspend fun loadListContactApiCall(token: String): ContactResponse {
        return apiService.loadListContact(token)
    }

    override suspend fun loadBookingDate(
        token: String,
        request: BookingDateCalendarRequest
    ): BookingDateCalendarResponse {
        return apiService.loadBookingDate(token, request)
    }

    override suspend fun bookingCancelListApiCall(
        token: String,
        request: BookingCancelListRequest
    ): BookingCancelListResponse {
        return apiService.bookingCancelList(token, request)
    }

    override suspend fun confirmBookingCancelApiCall(
        token: String,
        request: BookingCancelRequest
    ): DefaultApiResponse {
        return apiService.confirmBookingCancel(token, request)
    }

    override suspend fun numberBookingCancelApiCall(token: String) =
        apiService.numberBookingCancel(token)

    override suspend fun bookingCancelConversationApiCall(
        token: String,
        request: BookingCancelRequest
    ) = apiService.bookingCancelConversation(token, request)

    override suspend fun readMessageConversationApiCall(
        token: String,
        request: BookingCancelRequest
    ): DefaultApiResponse {
        return apiService.readMessageConversation(token, request)
    }

    override suspend fun sendMessageConversationApiCall(
        token: String,
        request: SendMessageConversationRequest
    ): DefaultApiResponse {
        return apiService.sendMessageConversation(token, request)
    }
}