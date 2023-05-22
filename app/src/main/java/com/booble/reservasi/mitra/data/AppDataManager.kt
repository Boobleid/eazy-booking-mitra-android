package com.booble.reservasi.mitra.data

import androidx.lifecycle.LiveData
import com.booble.reservasi.mitra.data.local.db.AppDbHelper
import com.booble.reservasi.mitra.data.local.pref.AppPreferencesHelper
import com.booble.reservasi.mitra.data.model.api.DefaultApiResponse
import com.booble.reservasi.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.reservasi.mitra.data.model.api.add_member.AddMemberServiceRequest
import com.booble.reservasi.mitra.data.model.api.add_member.DeleteMemberServiceRequest
import com.booble.reservasi.mitra.data.model.api.add_member.MemberServiceResponse
import com.booble.reservasi.mitra.data.model.api.balance_history.BalanceHistoryResponse
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
import com.booble.reservasi.mitra.data.model.db.movie.MovieEntity
import com.booble.reservasi.mitra.data.remote.AppApiHelper
import javax.inject.Inject

/**
 * Created by rivaldy on 10/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class AppDataManager @Inject constructor(
    private val api: AppApiHelper,
    private val db: AppDbHelper,
    private val pref: AppPreferencesHelper
) : DataManager {

    /** Remote Data - Fetch API **/
    override suspend fun getMoviesApiCall() = api.getMoviesApiCall()

    override suspend fun loginManualApiCall(request: LoginRequest) = api.loginManualApiCall(request)

    override suspend fun setForgotPasswordApiCall(request: ForgotPasswordRequest) = api.setForgotPasswordApiCall(request)

    override suspend fun getBookingUserApiCall(token: String, request: BookingUserRequest): BookingUserResponse {
        return api.getBookingUserApiCall(token, request)
    }

    override suspend fun getBookingUserDetailApiCall(token: String, request: BookingDetailRequest): BookingDetailResponse {
        return api.getBookingUserDetailApiCall(token, request)
    }

    override suspend fun verificationCheckInApiCall(token: String, request: VerificationCheckInRequest): VerificationCheckInResponse {
        return api.verificationCheckInApiCall(token, request)
    }

    override suspend fun verificationCheckOutApiCall(token: String, request: VerificationCheckInRequest): VerificationCheckInResponse {
        return api.verificationCheckOutApiCall(token, request)
    }

    override suspend fun verificationItemConditionApiCall(token: String, request: ItemConditionRequest): ItemConditionResponse {
        return api.verificationItemConditionApiCall(token, request)
    }

    override suspend fun getItemBookingApiCall(token: String, request: BookingItemRequest): BookingItemResponse {
        return api.getItemBookingApiCall(token, request)
    }

    override suspend fun getCheckOutHistoryApiCall(token: String, request: DefaultLimitOffsetRequest): CheckOutHistoryResponse {
        return api.getCheckOutHistoryApiCall(token, request)
    }

    override suspend fun getDetailCheckOutHistoryApiCall(token: String, request: DetailCheckOutHistoryRequest): DetailCheckOutHistoryResponse {
        return api.getDetailCheckOutHistoryApiCall(token, request)
    }

    override suspend fun getUserProfileApiCall(token: String, request: UserProfileRequest): UserProfileResponse {
        return api.getUserProfileApiCall(token, request)
    }

    override suspend fun setUpdateProfileApiCall(token: String, request: UpdateProfileRequest): UpdateProfileResponse {
        return api.setUpdateProfileApiCall(token, request)
    }

    // NEW API

    override suspend fun getServiceApiCall(token: String, request: ServiceRequest): ServiceResponse {
        return api.getServiceApiCall(token, request)
    }

    override suspend fun getCityApiCall(token: String): CityResponse {
        return api.getCityApiCall(token)
    }

    override suspend fun getServiceCategoryApiCall(token: String): CategoryResponse {
        return api.getServiceCategoryApiCall(token)
    }

    override suspend fun addServiceApiCall(token: String, request: AddServiceRequest): AddServiceResponse {
        return api.addServiceApiCall(token, request)
    }

    override suspend fun addFurnitureApiCall(token: String, request: AddFurnitureRequest): AddFurnitureResponse {
        return api.addFurnitureApiCall(token, request)
    }

    override suspend fun addRoomApiCall(token: String, request: AddRoomRequest): AddRoomResponse {
        return api.addRoomApiCall(token, request)
    }

    override suspend fun facilityListApiCall(
        token: String,
        request: FacilityListRequest
    ): FacilityListResponse {
        return api.facilityListApiCall(token, request)
    }

    override suspend fun addFacilityApiCall(token: String, request: AddFacilityRequest): AddFacilityResponse {
        return api.addFacilityApiCall(token, request)
    }

    override suspend fun getFurnitureApiCall(token: String): FurnitureResponse {
        return api.getFurnitureApiCall(token)
    }

    override suspend fun getFacilityBuildingApiCall(token: String): FacilityBuildingResponse {
        return api.getFacilityBuildingApiCall(token)
    }

    override suspend fun getFacilityRoomApiCall(token: String): FacilityRoomResponse {
        return api.getFacilityRoomApiCall(token)
    }

    override suspend fun getDetailRoomApiCall(token: String, request: DetailRoomRequest): DetailRoomResponse {
        return api.getDetailRoomApiCall(token, request)
    }

    override suspend fun getDetailFacilityApiCall(token: String, request: DetailFacilityRequest): DetailFacilityResponse {
        return api.getDetailFacilityApiCall(token, request)
    }

    override suspend fun getRoomFacilityApiCall(token: String, request: RoomFacilityRequest): RoomFacilityResponse {
        return api.getRoomFacilityApiCall(token, request)
    }

    override suspend fun setStatusDisplayApiCall(token: String, request: StatusDisplayRoomFacilityRequest): DefaultApiResponse {
        return api.setStatusDisplayApiCall(token, request)
    }

    override suspend fun addMemberServiceApiCall(token: String, request: AddMemberServiceRequest): DefaultApiResponse {
        return api.addMemberServiceApiCall(token, request)
    }

    override suspend fun getWithdrawApiCall(token: String, request: WithdrawRequest): DefaultApiResponse {
        return api.getWithdrawApiCall(token, request)
    }

    override suspend fun getMemberServiceApiCall(token: String): MemberServiceResponse {
        return api.getMemberServiceApiCall(token)
    }

    override suspend fun deleteMemberServiceApiCall(token: String, request: DeleteMemberServiceRequest): DefaultApiResponse {
        return api.deleteMemberServiceApiCall(token, request)
    }

    override suspend fun getBalanceHistoryApiCall(token: String): BalanceHistoryResponse {
        return api.getBalanceHistoryApiCall(token)
    }

    override suspend fun loadListHelpApiCall(token: String, request: HelpRequest) : HelpResponse {
        return api.loadListHelpApiCall(token, request)
    }

    override suspend fun loadListContactApiCall(token: String) : ContactResponse {
        return api.loadListContactApiCall(token)
    }

    override suspend fun loadBookingDate(
        token: String,
        request: BookingDateCalendarRequest
    ): BookingDateCalendarResponse {
        return api.loadBookingDate(token, request)
    }

    /** Local Data - Room Local Storage **/
    override suspend fun clearMovies() {
        db.clearMovies()
    }

    override suspend fun insertAllMovie(movies: MutableList<MovieEntity>) {
        db.insertAllMovie(movies)
    }

    override suspend fun insertMovie(movie: MovieEntity) {
        db.insertMovie(movie)
    }

    override fun getAllMovie(): LiveData<MutableList<MovieEntity>> {
        return db.getAllMovie()
    }

    override fun getMovieById(movieId: Int): LiveData<MovieEntity> {
        return db.getMovieById(movieId)
    }

    override suspend fun deleteMovie(movie: MovieEntity) {
        db.deleteMovie(movie)
    }

    override suspend fun deleteMovieById(movieId: Int) {
        db.deleteMovieById(movieId)
    }

    /** Local Data - SharedPreference Storage **/
    override fun setUrlAPI(url: String) {
        pref.setUrlAPI(url)
    }

    override fun getUrlAPI(): String {
        return pref.getUrlAPI()
    }

    override fun setAccessToken(token: String) {
        pref.setAccessToken(token)
    }

    override fun getAccessToken(): String {
        return pref.getAccessToken()
    }

    override fun setCurrentUserId(id: String) {
        return pref.setCurrentUserId(id)
    }

    override fun getCurrentUserId(): String {
        return pref.getCurrentUserId()
    }
}