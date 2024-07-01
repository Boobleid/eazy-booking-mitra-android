package com.booble.eazybooking.mitra.ui.booking_history.calendar_booking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booble.eazybooking.mitra.R
import com.booble.eazybooking.mitra.base.BaseBottomSheetFragment
import com.booble.eazybooking.mitra.data.model.api.calendar.BookingDateCalendarRequest
import com.booble.eazybooking.mitra.data.model.api.calendar.BookingDateCalendarResponse
import com.booble.eazybooking.mitra.data.model.api.facility.FacilityItem
import com.booble.eazybooking.mitra.data.model.api.facility.FacilityListRequest
import com.booble.eazybooking.mitra.data.model.api.facility.FacilityListResponse
import com.booble.eazybooking.mitra.data.model.api.service.ServiceData
import com.booble.eazybooking.mitra.data.model.offline.CalendarModel
import com.booble.eazybooking.mitra.data.model.offline.LocationFilter
import com.booble.eazybooking.mitra.data.network.DataResource
import com.booble.eazybooking.mitra.databinding.FragmentCalendarBookingBottomSheetBinding
import com.booble.eazybooking.mitra.ui.booking_history.BookingHistoryViewModel
import com.booble.eazybooking.mitra.utils.UtilConstants.STR_ALL
import com.booble.eazybooking.mitra.utils.UtilExtensions.setTextEditable
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CalendarBookingBottomSheetFragment(
    private val locationList: ArrayList<ServiceData>,
    private val setFilterLocation: (LocationFilter?) -> Unit
) :
    BaseBottomSheetFragment<FragmentCalendarBookingBottomSheetBinding>() {
    private val historyViewModel by viewModels<BookingHistoryViewModel>()

    private val DAYS_COUNT = 42
    private val calendarList = ArrayList<CalendarModel>()
    private val calendar = Calendar.getInstance() //default calendar pada tanggal hari ini
    private var tahun: Int = -1
    private var monthOfYear: Int = -1

    private var locationFilter: LocationFilter? = null
    private var selectedDate = ""

    private val calendarAdapter by lazy {
        CalendarAdapter(
            calendarList
        ) { date -> selectedDate = date }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCalendarBookingBottomSheetBinding
        get() = FragmentCalendarBookingBottomSheetBinding::inflate

    override fun initView() {
        initData()
        initUI()
        initClick()
        setLocationList()
        setCalendar()
    }

    override fun initObserver() {
        historyViewModel.bookingDate.observe(this) {
            when (it) {
                is DataResource.Loading -> {}
                is DataResource.Success -> setBookingDate(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }

        historyViewModel.facilityList.observe(this) {
            when (it) {
                is DataResource.Loading -> {}
                is DataResource.Success -> setFacilityList(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun initData() {
        locationFilter = arguments?.getParcelable(EXTRA_FILTER) ?: LocationFilter()
        selectedDate = locationFilter?.date ?: ""
    }

    private fun initAPI() {
        val request = BookingDateCalendarRequest(
            tahun.toString(),
            (monthOfYear + 1).toString(),
            locationFilter?.locationId,
            locationFilter?.facilityId
        )
        historyViewModel.loadBookingDateApiCall(request)
    }

    private fun initUI() {
        binding.dateRV.layoutManager = GridLayoutManager(context, 7)
        binding.dateRV.adapter = calendarAdapter

        if (locationFilter != null) {
            binding.locationACTV.setTextEditable(locationFilter?.locationName ?: STR_ALL)
            binding.facilityACTV.setTextEditable(locationFilter?.facilityName ?: STR_ALL)
        }
    }

    private fun initClick() {
        binding.prevMonthIV.setOnClickListener { moveToPreviousMonth() }
        binding.nextMonthIV.setOnClickListener { moveToTheNextMonth() }
        binding.filterMB.setOnClickListener { filterLocation() }
        binding.cancelMB.setOnClickListener { dismiss() }
    }

    private fun setLocationList() {
        val listData = mutableListOf<ServiceData>()
        listData.add(ServiceData(id = null, nama = STR_ALL))
        locationList.let { listData.addAll(it) }

        val adapter = ArrayAdapter(requireActivity(), R.layout.layout_spinner_item, listData)
        binding.locationACTV.setAdapter(adapter)
        binding.locationACTV.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                val location = adapterView.getItemAtPosition(position) as ServiceData

                val id = location.id.toString()
                locationFilter?.locationId = id
                locationFilter?.locationName = location.nama

                val request = FacilityListRequest(id)
                historyViewModel.facilityListApiCall(request)
            }
    }

    private fun setFacilityList(response: FacilityListResponse) {
        val facilityList = response.data
        val listData = mutableListOf<FacilityItem>()
        listData.add(FacilityItem(id = null, productName = STR_ALL))
        facilityList?.let { listData.addAll(it) }

        val adapter = ArrayAdapter(requireActivity(), R.layout.layout_spinner_item, listData)
        binding.facilityACTV.setAdapter(adapter)
        binding.facilityACTV.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                val facility = adapterView.getItemAtPosition(position) as FacilityItem

                locationFilter?.facilityId = facility.id.toString()
                locationFilter?.facilityName = facility.productName
            }
    }

    private fun setBookingDate(response: BookingDateCalendarResponse) {
        val isBookingInLastMonth = response.lastMonth
            ?: 0 //variabel untuk mengecek apakah ada terdapat booking di bulan lalu
        val isBookingInNextMonth = response.nextMonth
            ?: 0 //variabel untuk mengecek apakah ada terdapat booking di bulan depan

        if (isBookingInLastMonth > 0) binding.dotPrevious.visibility = View.VISIBLE
        else binding.dotPrevious.visibility = View.GONE

        if (isBookingInNextMonth > 0) binding.dotNext.visibility = View.VISIBLE
        else binding.dotNext.visibility = View.GONE

        val list = response.data
        if (list != null) {
            for (date in list) {
                calendarList.find { it.date == date.day && it.month == monthOfYear }?.let {
                    it.isBooking = true
                }
            }
        }

        calendarAdapter.notifyDataSetChanged()
    }

    private fun moveToPreviousMonth() {
        if (monthOfYear == Calendar.JANUARY) {
            tahun -= 1
            monthOfYear = Calendar.DECEMBER
            calendar.set(Calendar.YEAR, tahun)
            calendar.set(Calendar.MONTH, Calendar.DECEMBER)
        } else {
            monthOfYear -= 1
            calendar.set(Calendar.MONTH, monthOfYear)
        }

        setCalendar()
    }

    private fun moveToTheNextMonth() {
        if (monthOfYear == Calendar.DECEMBER) {
            tahun += 1
            monthOfYear = Calendar.JANUARY
            calendar.set(Calendar.YEAR, tahun)
            calendar.set(Calendar.MONTH, Calendar.JANUARY)
        } else {
            monthOfYear += 1
            calendar.set(Calendar.MONTH, monthOfYear)
        }

        setCalendar()
    }

    private fun filterLocation() {
        locationFilter?.date = selectedDate
        setFilterLocation(locationFilter)

        dismiss()
    }

    private fun setCalendar() {
        val cells =
            ArrayList<CalendarModel>() // inisialisasi variabel untuk setiap tanggal kalender

        // set variabel tahun dan monthOfYear ke tahun dan bulan sekarang
        tahun = calendar.get(Calendar.YEAR)
        monthOfYear = calendar.get(Calendar.MONTH)

        var sdf =
            SimpleDateFormat("MMMM yyyy", Locale("in", "ID"))  // obyek untuk parse bulan dan tahun
        val dateToday = sdf.format(calendar.time) //format obyek calendar
        binding.periodTV.text = dateToday

        //calendarToday
        val calendarCompare = Calendar.getInstance() //instansiasi obyek calendar pembanding

        calendarCompare.set(
            Calendar.MONTH,
            monthOfYear
        ) //set bulan pada calendar pembanding ke monthOfYear
        calendarCompare.set(Calendar.YEAR, tahun) //set tahun pada calendar pembanding ke tahun

        // memnentukan kapan tanggal dimulai pada bulan
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        var monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 2

        // karena didesain hari minggu ditempatkan di hari ketujuh sedangkan pada library Calendar hari minggu di hari pertama (index 0)
        // maka jika hari pertama pada bulan baru jatuh di hari minggu maka set menjadi 6 jika tidak maka tanggal bakal tidak muncul karena -1
        if (monthBeginningCell == -1) monthBeginningCell = 6

        // pindah calendar ke awal minggu
        calendar.add(Calendar.DAY_OF_MONTH, - monthBeginningCell)

        //obyek untuk parse tanggal
        sdf = SimpleDateFormat("yyyy-MM-dd", Locale("in", "ID"))

        // isi tanggal
        calendarAdapter.selectedItemPosition =
            RecyclerView.NO_POSITION //agar ketika bulan berubah, tanggal yg sebelumnya terpilih bakal hilang
        while (cells.size < DAYS_COUNT) {
            if (sdf.format(calendar.time).equals(selectedDate)) {
                cells.add(
                    CalendarModel(
                        calendar.get(Calendar.DATE),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        calendarCompare,
                        false,
                    )
                )
                calendarAdapter.selectedItemPosition = cells.size - 1
            } else {
                cells.add(
                    CalendarModel(
                        calendar.get(Calendar.DATE),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        calendarCompare,
                        false,
                    )
                )
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        calendarList.clear()
        calendarList.addAll(cells)

        initAPI()
    }

    private fun showFailure(failure: DataResource.Failure) {
    }

    companion object {
        const val EXTRA_FILTER = "EXTRA_FILTER"
    }
}
