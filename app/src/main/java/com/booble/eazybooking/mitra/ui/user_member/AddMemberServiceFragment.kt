package com.booble.eazybooking.mitra.ui.user_member

import androidx.fragment.app.viewModels
import com.booble.eazybooking.mitra.base.BaseFragment
import com.booble.eazybooking.mitra.data.model.api.DefaultApiResponse
import com.booble.eazybooking.mitra.data.model.api.add_member.DeleteMemberServiceRequest
import com.booble.eazybooking.mitra.data.model.api.add_member.MemberServiceData
import com.booble.eazybooking.mitra.data.model.api.add_member.MemberServiceResponse
import com.booble.eazybooking.mitra.data.network.DataResource
import com.booble.eazybooking.mitra.databinding.FragmentAddMemberServiceBinding
import com.booble.eazybooking.mitra.utils.UtilExceptions.handleApiError
import com.booble.eazybooking.mitra.utils.UtilExtensions.isVisible
import com.booble.eazybooking.mitra.utils.UtilExtensions.myToast
import com.booble.eazybooking.mitra.utils.UtilExtensions.openActivity
import com.booble.eazybooking.mitra.utils.UtilFunctions
import com.booble.eazybooking.mitra.utils.UtilFunctions.openAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMemberServiceFragment : BaseFragment<FragmentAddMemberServiceBinding>(FragmentAddMemberServiceBinding::inflate) {
    private val addMemberServiceViewModel by viewModels<AddMemberServiceViewModel>()
    private val memberServiceAdapter by lazy {
        MemberServiceAdapter({ editClickService(it) }, { deleteClickService(it) })
    }

    override fun initView() {
        binding.listDataRV.adapter = memberServiceAdapter
        initClick()
        initListener()
    }

    override fun initObservers() {
        addMemberServiceViewModel.getMemberServiceApiCall()
        addMemberServiceViewModel.getMemberService.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewMember(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
        addMemberServiceViewModel.deleteMemberService.observe(this, {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showViewDelete(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        })
    }

    override fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        binding.swipeRefresh.isRefreshing = false
        handleApiError(failure)
    }

    override fun onResume() {
        super.onResume()
        addMemberServiceViewModel.getMemberServiceApiCall()
    }

    private fun initClick() {
        binding.addMB.setOnClickListener {
            requireContext().openActivity(AddMemberServiceActivity::class.java)
        }
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            addMemberServiceViewModel.getMemberServiceApiCall()
        }
    }

    private fun showViewDelete(response: DefaultApiResponse) {
        showLoading(false)
        requireContext().myToast(response.message.toString())
        addMemberServiceViewModel.getMemberServiceApiCall()
    }

    private fun showViewMember(response: MemberServiceResponse) {
        showLoading(false)
        binding.swipeRefresh.isRefreshing = false
        memberServiceAdapter.submitList(response.data)
        binding.noDataTV.isVisible(response.data?.size == 0)
    }

    private fun deleteClickService(data: MemberServiceData) {
        val title = "Hapus service"
        val message = "Anda yakin ingin menghapus ${data.nama}?"
        openAlertDialog(requireContext(), title, message, object : UtilFunctions.IDialogButtonClickListener {
            override fun onPositiveButtonClick() {
                addMemberServiceViewModel.deleteMemberServiceApiCall(DeleteMemberServiceRequest(data.id.toString()))
            }

            override fun onNegativeButtonClick() {
            }
        })
    }

    private fun editClickService(data: MemberServiceData) {
        requireContext().openActivity(AddMemberServiceActivity::class.java) {
            putParcelable(AddMemberServiceActivity.EXTRA_MEMBER_SERVICE, data)
        }
    }
}