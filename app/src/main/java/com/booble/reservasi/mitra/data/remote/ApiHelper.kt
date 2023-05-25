package com.booble.reservasi.mitra.data.remote

import com.booble.reservasi.mitra.data.model.api.DefaultApiResponse
import com.booble.reservasi.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.reservasi.mitra.data.model.api.add_member.AddMemberServiceRequest
import com.booble.reservasi.mitra.data.model.api.add_member.DeleteMemberServiceRequest
import com.booble.reservasi.mitra.data.model.api.add_member.MemberServiceResponse
import com.booble.reservasi.mitra.data.model.api.balance_history.BalanceHistoryResponse
import com.booble.reservasi.mitra.data.model.api.booking_cancel.*
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
import com.booble.reservasi.mitra.data.model.api.forgot_password.ForgotPasswordResponse
import com.booble.reservasi.mitra.data.model.api.help.HelpRequest
import com.booble.reservasi.mitra.data.model.api.help.HelpResponse
import com.booble.reservasi.mitra.data.model.api.help.contact.ContactResponse
import com.booble.reservasi.mitra.data.model.api.login.LoginRequest
import com.booble.reservasi.mitra.data.model.api.login.LoginResponse
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

/**
 * Created by rivaldy on 07/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

interface ApiHelper {
    suspend fun getMoviesApiCall(): com.booble.reservasi.mitra.data.model.api.movie.MovieResponse
    suspend fun loginManualApiCall(request: LoginRequest): LoginResponse
    suspend fun setForgotPasswordApiCall(request: ForgotPasswordRequest): ForgotPasswordResponse
    suspend fun getBookingUserApiCall(token: String, request: BookingUserRequest): BookingUserResponse
    suspend fun getBookingUserDetailApiCall(token: String, request: BookingDetailRequest): BookingDetailResponse
    suspend fun verificationCheckInApiCall(token: String, request: VerificationCheckInRequest): VerificationCheckInResponse
    suspend fun verificationCheckOutApiCall(token: String, request: VerificationCheckInRequest): VerificationCheckInResponse
    suspend fun verificationItemConditionApiCall(token: String, request: ItemConditionRequest): ItemConditionResponse
    suspend fun getItemBookingApiCall(token: String, request: BookingItemRequest): BookingItemResponse

    suspend fun getCheckOutHistoryApiCall(token: String, request: DefaultLimitOffsetRequest): CheckOutHistoryResponse
    suspend fun getDetailCheckOutHistoryApiCall(token: String, request: DetailCheckOutHistoryRequest): DetailCheckOutHistoryResponse
    suspend fun getUserProfileApiCall(token: String, request: UserProfileRequest): UserProfileResponse
    suspend fun setUpdateProfileApiCall(token: String, request: UpdateProfileRequest): UpdateProfileResponse

    // NEW API
    suspend fun getServiceApiCall(token: String, request: ServiceRequest): ServiceResponse
    suspend fun getCityApiCall(token: String): CityResponse
    suspend fun getServiceCategoryApiCall(token: String): CategoryResponse
    suspend fun addServiceApiCall(token: String, request: AddServiceRequest): AddServiceResponse
    suspend fun addFurnitureApiCall(token: String, request: AddFurnitureRequest): AddFurnitureResponse
    suspend fun addRoomApiCall(token: String, request: AddRoomRequest): AddRoomResponse
    suspend fun facilityListApiCall(token: String, request: FacilityListRequest): FacilityListResponse
    suspend fun addFacilityApiCall(token: String, request: AddFacilityRequest): AddFacilityResponse
    suspend fun getFurnitureApiCall(token: String): FurnitureResponse
    suspend fun getFacilityBuildingApiCall(token: String): FacilityBuildingResponse
    suspend fun getFacilityRoomApiCall(token: String): FacilityRoomResponse
    suspend fun getDetailRoomApiCall(token: String, request: DetailRoomRequest): DetailRoomResponse
    suspend fun getDetailFacilityApiCall(token: String, request: DetailFacilityRequest): DetailFacilityResponse
    suspend fun getRoomFacilityApiCall(token: String, request: RoomFacilityRequest): RoomFacilityResponse
    suspend fun setStatusDisplayApiCall(token: String, request: StatusDisplayRoomFacilityRequest): DefaultApiResponse
    suspend fun addMemberServiceApiCall(token: String, request: AddMemberServiceRequest): DefaultApiResponse
    suspend fun getWithdrawApiCall(token: String, request: WithdrawRequest): DefaultApiResponse
    suspend fun getMemberServiceApiCall(token: String): MemberServiceResponse
    suspend fun deleteMemberServiceApiCall(token: String, request: DeleteMemberServiceRequest): DefaultApiResponse
    suspend fun getBalanceHistoryApiCall(token: String): BalanceHistoryResponse
    suspend fun loadListHelpApiCall(token: String, request: HelpRequest): HelpResponse
    suspend fun loadListContactApiCall(token: String): ContactResponse
    suspend fun loadBookingDate(token: String, request: BookingDateCalendarRequest): BookingDateCalendarResponse

    suspend fun bookingCancelListApiCall(token: String, request: BookingCancelListRequest): BookingCancelListResponse
    suspend fun confirmBookingCancelApiCall(token: String, request: BookingCancelRequest): DefaultApiResponse
    suspend fun bookingCancelConversationApiCall(token: String, request: BookingCancelRequest): CancelConversationResponse
    suspend fun readMessageConversationApiCall(token: String, request: BookingCancelRequest): DefaultApiResponse
    suspend fun sendMessageConversationApiCall(token: String, request: SendMessageConversationRequest): DefaultApiResponse
}