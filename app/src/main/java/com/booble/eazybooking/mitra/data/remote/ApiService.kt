package com.booble.eazybooking.mitra.data.remote

import com.booble.eazybooking.mitra.data.model.api.DefaultApiResponse
import com.booble.eazybooking.mitra.data.model.api.DefaultLimitOffsetRequest
import com.booble.eazybooking.mitra.data.model.api.add_member.AddMemberServiceRequest
import com.booble.eazybooking.mitra.data.model.api.add_member.DeleteMemberServiceRequest
import com.booble.eazybooking.mitra.data.model.api.add_member.MemberServiceResponse
import com.booble.eazybooking.mitra.data.model.api.balance_history.BalanceHistoryResponse
import com.booble.eazybooking.mitra.data.model.api.booking_cancel.*
import com.booble.eazybooking.mitra.data.model.api.booking_user.BookingUserRequest
import com.booble.eazybooking.mitra.data.model.api.booking_user.BookingUserResponse
import com.booble.eazybooking.mitra.data.model.api.booking_user.booking_detail.BookingDetailRequest
import com.booble.eazybooking.mitra.data.model.api.booking_user.booking_detail.BookingDetailResponse
import com.booble.eazybooking.mitra.data.model.api.booking_user.booking_item.BookingItemRequest
import com.booble.eazybooking.mitra.data.model.api.booking_user.booking_item.BookingItemResponse
import com.booble.eazybooking.mitra.data.model.api.booking_user.item_condition.ItemConditionRequest
import com.booble.eazybooking.mitra.data.model.api.booking_user.item_condition.ItemConditionResponse
import com.booble.eazybooking.mitra.data.model.api.calendar.BookingDateCalendarRequest
import com.booble.eazybooking.mitra.data.model.api.calendar.BookingDateCalendarResponse
import com.booble.eazybooking.mitra.data.model.api.check_history.CheckOutHistoryResponse
import com.booble.eazybooking.mitra.data.model.api.check_history.detail.DetailCheckOutHistoryRequest
import com.booble.eazybooking.mitra.data.model.api.check_history.detail.DetailCheckOutHistoryResponse
import com.booble.eazybooking.mitra.data.model.api.facility.FacilityListRequest
import com.booble.eazybooking.mitra.data.model.api.facility.FacilityListResponse
import com.booble.eazybooking.mitra.data.model.api.forgot_password.ForgotPasswordRequest
import com.booble.eazybooking.mitra.data.model.api.forgot_password.ForgotPasswordResponse
import com.booble.eazybooking.mitra.data.model.api.help.HelpRequest
import com.booble.eazybooking.mitra.data.model.api.help.HelpResponse
import com.booble.eazybooking.mitra.data.model.api.help.contact.ContactResponse
import com.booble.eazybooking.mitra.data.model.api.login.LoginRequest
import com.booble.eazybooking.mitra.data.model.api.login.LoginResponse
import com.booble.eazybooking.mitra.data.model.api.master.category.CategoryResponse
import com.booble.eazybooking.mitra.data.model.api.master.city.CityResponse
import com.booble.eazybooking.mitra.data.model.api.master.facility_building.FacilityBuildingResponse
import com.booble.eazybooking.mitra.data.model.api.master.facility_room.FacilityRoomResponse
import com.booble.eazybooking.mitra.data.model.api.master.furniture.FurnitureResponse
import com.booble.eazybooking.mitra.data.model.api.movie.MovieResponse
import com.booble.eazybooking.mitra.data.model.api.room_facility.RoomFacilityRequest
import com.booble.eazybooking.mitra.data.model.api.room_facility.RoomFacilityResponse
import com.booble.eazybooking.mitra.data.model.api.room_facility.status_display.StatusDisplayRoomFacilityRequest
import com.booble.eazybooking.mitra.data.model.api.service.ServiceRequest
import com.booble.eazybooking.mitra.data.model.api.service.ServiceResponse
import com.booble.eazybooking.mitra.data.model.api.service.add_facility.AddFacilityRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_facility.AddFacilityResponse
import com.booble.eazybooking.mitra.data.model.api.service.add_furniture.AddFurnitureRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_furniture.AddFurnitureResponse
import com.booble.eazybooking.mitra.data.model.api.service.add_room.AddRoomRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_room.AddRoomResponse
import com.booble.eazybooking.mitra.data.model.api.service.add_service.AddServiceRequest
import com.booble.eazybooking.mitra.data.model.api.service.add_service.AddServiceResponse
import com.booble.eazybooking.mitra.data.model.api.service.detail_facility.DetailFacilityRequest
import com.booble.eazybooking.mitra.data.model.api.service.detail_facility.DetailFacilityResponse
import com.booble.eazybooking.mitra.data.model.api.service.detail_room.DetailRoomRequest
import com.booble.eazybooking.mitra.data.model.api.service.detail_room.DetailRoomResponse
import com.booble.eazybooking.mitra.data.model.api.user_profile.UserProfileRequest
import com.booble.eazybooking.mitra.data.model.api.user_profile.UserProfileResponse
import com.booble.eazybooking.mitra.data.model.api.user_profile.update_profile.UpdateProfileRequest
import com.booble.eazybooking.mitra.data.model.api.user_profile.update_profile.UpdateProfileResponse
import com.booble.eazybooking.mitra.data.model.api.verfication_check_in.VerificationCheckInRequest
import com.booble.eazybooking.mitra.data.model.api.verfication_check_in.VerificationCheckInResponse
import com.booble.eazybooking.mitra.data.model.api.withdraw.WithdrawRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by rivaldy on 01/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovies(): MovieResponse

    @POST(PATH_FORGOT_PASSWORD)
    suspend fun setForgotPassword(@Body request: ForgotPasswordRequest): ForgotPasswordResponse

    @POST(PATH_BOOKING_USER)
    suspend fun getBookingUser(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: BookingUserRequest): BookingUserResponse

    @POST(PATH_BOOKING_DETAIL)
    suspend fun getBookingUserDetail(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: BookingDetailRequest): BookingDetailResponse

    @POST(PATH_VERIFICATION_CHECK_IN)
    suspend fun verificationCheckIn(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: VerificationCheckInRequest): VerificationCheckInResponse

    @POST(PATH_VERIFICATION_CHECK_OUT)
    suspend fun verificationCheckOut(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: VerificationCheckInRequest): VerificationCheckInResponse

    @POST(PATH_VERIFICATION_ITEM_CONDITION)
    suspend fun verificationItemCondition(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: ItemConditionRequest): ItemConditionResponse

    @POST(PATH_ITEM_BOOKING)
    suspend fun getItemBooking(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: BookingItemRequest): BookingItemResponse

    @POST(PATH_CHECK_OUT_HISTORY)
    suspend fun getCheckOutHistory(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: DefaultLimitOffsetRequest): CheckOutHistoryResponse

    @POST(PATH_DETAIL_CHECK_OUT_HISTORY)
    suspend fun getDetailCheckOutHistory(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: DetailCheckOutHistoryRequest): DetailCheckOutHistoryResponse

    @POST(PATH_UPDATE_USER_PROFILE)
    suspend fun setUpdateProfile(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: UpdateProfileRequest): UpdateProfileResponse

    // NEW API

    @POST(PATH_LOGIN)
    suspend fun loginManual(@Body request: LoginRequest): LoginResponse

    @POST(PATH_SERVICE)
    suspend fun getService(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: ServiceRequest): ServiceResponse

    @POST(PATH_USER_PROFILE)
    suspend fun getUserProfile(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: UserProfileRequest): UserProfileResponse

    @POST(PATH_CITY)
    suspend fun getCity(@Header(HEADER_X_ACCESS_TOKEN) token: String): CityResponse

    @POST(PATH_CATEGORY)
    suspend fun getServiceCategory(@Header(HEADER_X_ACCESS_TOKEN) token: String): CategoryResponse

    @POST(PATH_ADD_SERVICE)
    suspend fun addService(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: AddServiceRequest): AddServiceResponse

    @POST(PATH_ADD_FURNITURE)
    suspend fun addFurniture(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: AddFurnitureRequest): AddFurnitureResponse

    @POST(PATH_ADD_ROOM)
    suspend fun addRoom(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: AddRoomRequest): AddRoomResponse

    @POST(PATH_FACILITY_LIST)
    suspend fun facilityList(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: FacilityListRequest): FacilityListResponse

    @POST(PATH_ADD_FACILITY)
    suspend fun addFacility(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: AddFacilityRequest): AddFacilityResponse

    @POST(PATH_LIST_FURNITURE)
    suspend fun getFurniture(@Header(HEADER_X_ACCESS_TOKEN) token: String): FurnitureResponse

    @POST(PATH_FACILITY_BUILDING)
    suspend fun getFacilityBuilding(@Header(HEADER_X_ACCESS_TOKEN) token: String): FacilityBuildingResponse

    @POST(PATH_FACILITY_ROOM)
    suspend fun getFacilityRoom(@Header(HEADER_X_ACCESS_TOKEN) token: String): FacilityRoomResponse

    @POST(PATH_ROOM_DETAIL)
    suspend fun getDetailRoom(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: DetailRoomRequest): DetailRoomResponse

    @POST(PATH_FACILITY_DETAIL)
    suspend fun getDetailFacility(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: DetailFacilityRequest): DetailFacilityResponse

    @POST(PATH_LIST_ROOM_FACILITY)
    suspend fun getRoomFacility(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: RoomFacilityRequest): RoomFacilityResponse

    @POST(PATH_UPDATE_STATUS_DISPLAY_ROOM_FACILITY)
    suspend fun setUpdateStatusDisplay(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: StatusDisplayRoomFacilityRequest): DefaultApiResponse

    @POST(PATH_ADD_MEMBER_SERVICE)
    suspend fun addMemberService(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: AddMemberServiceRequest): DefaultApiResponse

    @POST(PATH_WITHDRAW)
    suspend fun getWithdraw(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: WithdrawRequest): DefaultApiResponse

    @POST(PATH_MEMBER_SERVICE)
    suspend fun getMemberService(@Header(HEADER_X_ACCESS_TOKEN) token: String): MemberServiceResponse

    @POST(PATH_DELETE_MEMBER_SERVICE)
    suspend fun deleteMemberService(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: DeleteMemberServiceRequest): DefaultApiResponse

    @POST(PATH_BALANCE_HISTORY)
    suspend fun getBalanceHistory(@Header(HEADER_X_ACCESS_TOKEN) token: String): BalanceHistoryResponse

    @POST(PATH_LIST_HELP)
    suspend fun loadListHelp(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: HelpRequest): HelpResponse

    @POST(PATH_LIST_CONTACT)
    suspend fun loadListContact(@Header(HEADER_X_ACCESS_TOKEN) token: String): ContactResponse

    @POST(PATH_BOOKING_DATE_LIST)
    suspend fun loadBookingDate(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: BookingDateCalendarRequest): BookingDateCalendarResponse

    @POST(PATH_CANCEL_BOOKING_LIST)
    suspend fun bookingCancelList(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: BookingCancelListRequest): BookingCancelListResponse

    @POST(PATH_CONFIRM_CANCEL_BOOKING)
    suspend fun confirmBookingCancel(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: BookingCancelRequest): DefaultApiResponse

    @GET(PATH_NUMBER_BOOKING_CANCEL)
    suspend fun numberBookingCancel(@Header(HEADER_X_ACCESS_TOKEN) token: String): NumberBookingCancelResponse

    @POST(PATH_CONVERSATION_CANCEL_BOOKING)
    suspend fun bookingCancelConversation(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: BookingCancelRequest): CancelConversationResponse

    @POST(PATH_READ_MESSAGE_CONVERSATION)
    suspend fun readMessageConversation(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: BookingCancelRequest): DefaultApiResponse

    @POST(PATH_SEND_MESSAGE_CONVERSATION)
    suspend fun sendMessageConversation(@Header(HEADER_X_ACCESS_TOKEN) token: String, @Body request: SendMessageConversationRequest): DefaultApiResponse

    companion object {
        const val HEADER_X_ACCESS_TOKEN = "x-access-token"

        const val PATH_FORGOT_PASSWORD = "/auth/lupa_password"
        const val PATH_BOOKING_USER = "/utama/data_booking"
        const val PATH_BOOKING_DETAIL = "/utama/detail_data_booking"
        const val PATH_VERIFICATION_CHECK_IN = "/proses/verifikasi_checkin"
        const val PATH_VERIFICATION_CHECK_OUT = "/proses/verifikasi_checkout"
        const val PATH_ITEM_BOOKING = "/utama/list_data_barang"
        const val PATH_VERIFICATION_ITEM_CONDITION = "/proses/verifikasi_kondisi_barang"

        const val PATH_CHECK_OUT_HISTORY = "/list/riwayat_booking"
        const val PATH_DETAIL_CHECK_OUT_HISTORY = "/histori_detail_data_booking"

        // NEW API
        const val PATH_LOGIN = "/auth/login"
        const val PATH_SERVICE = "/list/layanan"
        const val PATH_USER_PROFILE = "/auth/profil"
        const val PATH_CITY = "/list/kota"
        const val PATH_CATEGORY = "/list/kategori_layanan"
        const val PATH_ADD_SERVICE = "/simpan/simpan_layanan"
        const val PATH_ADD_FURNITURE = "/simpan/simpan_perabotan"
        const val PATH_FACILITY_LIST = "/list/list_fasilitas"
        const val PATH_ADD_ROOM = "/simpan/simpan_kamar"
        const val PATH_ADD_FACILITY = "/simpan/simpan_fasilitas"
        const val PATH_LIST_FURNITURE = "/list/perabotan"
        const val PATH_FACILITY_BUILDING = "/list/fasilitas_gedung"
        const val PATH_FACILITY_ROOM = "/list/fasilitas_ruangan"
        const val PATH_UPDATE_USER_PROFILE = "/simpan/simpan_profil"
        const val PATH_ROOM_DETAIL = "/detail/kamar"
        const val PATH_FACILITY_DETAIL = "/detail/fasilitas"
        const val PATH_LIST_ROOM_FACILITY = "/list/kamar_fasilitas"
        const val PATH_UPDATE_STATUS_DISPLAY_ROOM_FACILITY = "/simpan/update_status"
        const val PATH_ADD_MEMBER_SERVICE = "/simpan/simpan_service"
        const val PATH_WITHDRAW = "/simpan/simpan_wd"
        const val PATH_MEMBER_SERVICE = "/list/service"
        const val PATH_DELETE_MEMBER_SERVICE = "/list/hapus_service"
        const val PATH_BALANCE_HISTORY = "/detail/riwayat_bonus"
        const val PATH_LIST_HELP = "/list/list_bantuan"
        const val PATH_LIST_CONTACT = "/list/list_kontak"
        const val PATH_BOOKING_DATE_LIST = "/list/kalender_booking"

        const val PATH_CANCEL_BOOKING_LIST = "/list/riwayat_pembatalan"
        const val PATH_CONFIRM_CANCEL_BOOKING = "/simpan/konfir_pembatalan"
        const val PATH_NUMBER_BOOKING_CANCEL = "/list/permintaan_pembatalan"
        const val PATH_CONVERSATION_CANCEL_BOOKING = "/list/percakapan_pembatalan"
        const val PATH_READ_MESSAGE_CONVERSATION = "/simpan/update_status_baca_percakapan"
        const val PATH_SEND_MESSAGE_CONVERSATION = "/simpan/kirim_pesan"
    }
}