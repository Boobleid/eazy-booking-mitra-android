package com.booble.reservasi.mitra.back_up.ui.chat

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.base.BaseActivity
import com.booble.reservasi.mitra.data.model.api.booking_user.BookingUserData
import com.booble.reservasi.mitra.data.model.api.firebase.ChatModel
import com.booble.reservasi.mitra.data.network.DataResource
import com.booble.reservasi.mitra.databinding.ActivityChatBinding
import com.booble.reservasi.mitra.ui.MasterViewModel
import com.booble.reservasi.mitra.back_up.ui.home.detail_order.DetailOrderActivity
import com.booble.reservasi.mitra.utils.UtilConstants.CHILD_IS_SEEN
import com.booble.reservasi.mitra.utils.UtilConstants.DATA_CHAT
import com.booble.reservasi.mitra.utils.UtilConstants.PARENT_APP
import com.booble.reservasi.mitra.utils.UtilExtensions.isVisible
import com.booble.reservasi.mitra.utils.UtilExtensions.myToast
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ChatActivity : BaseActivity<ActivityChatBinding>() {
    private val viewModel by viewModels<MasterViewModel>()
    private val adapterChat: ChatAdapter by lazy {
        ChatAdapter(this, listChat)
    }

    private var extraBookingUserData: BookingUserData? = null
    private lateinit var refDataChat: DatabaseReference
    private lateinit var refSendChat: DatabaseReference
    private lateinit var refReadChat: DatabaseReference
    private var seenListener: ValueEventListener? = null

    private var listChat: ArrayList<ChatModel> = ArrayList()

    override fun getViewBinding() = ActivityChatBinding.inflate(layoutInflater)

    override fun initView() {
        binding.rvChat.adapter = adapterChat
        initData()
        setupUI()
        setupListener()
    }

    private fun initData() {
        extraBookingUserData = intent.getParcelableExtra(DetailOrderActivity.EXTRA_BOOKING_USER_DATA)
        if (extraBookingUserData != null) {
            binding.tvTitleBar.text = extraBookingUserData?.firstName.toString()
            retrieveDataMessage(viewModel.getCurrentUserId(), extraBookingUserData?.idMember.toString(), extraBookingUserData?.idTransaksi.toString())
            seenMessage(extraBookingUserData?.idMember.toString(), extraBookingUserData?.idTransaksi.toString())
        }
    }

    override fun initObservers() {
    }

    override fun showFailure(failure: DataResource.Failure) {
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener { onBackPressed() }
            ivSend.setOnClickListener {
                val message = etMessage.text.toString()
                if (message.isEmpty()) {
                    myToast(getString(R.string.pesan_tidak_boleh_kosong))
                } else {
                    sendMessage(viewModel.getCurrentUserId(), extraBookingUserData?.idMember.toString(), message, extraBookingUserData?.idTransaksi.toString())
                    etMessage.setText("")
                }
            }
        }
    }

    private fun setupUI() {
        setupShimmer(true)
        adapterChat.myId = viewModel.getCurrentUserId()
        with(binding) {
            rvChat.apply {
                setHasFixedSize(true)
                val linearLayoutManager = LinearLayoutManager(this@ChatActivity, RecyclerView.VERTICAL, false)
                linearLayoutManager.stackFromEnd = true
                layoutManager = linearLayoutManager
            }
        }
    }

    private fun retrieveDataMessage(myId: String, idUser: String, invoice: String) {
        refDataChat = FirebaseDatabase.getInstance().getReference(PARENT_APP).child(DATA_CHAT).child(invoice)
        refDataChat.keepSynced(true)
        refDataChat.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listChat.clear()
                for (snapshot in dataSnapshot.children) {
                    val chat: ChatModel? = snapshot.getValue(ChatModel::class.java)
                    chat?.let {
//                        if (chat.receiver.equals(myId) && chat.sender.equals(idUser) ||
//                            chat.receiver.equals(idUser) && chat.sender.equals(myId)
//                        ) {
                            listChat.add(chat)
//                        }
                    }
                }
                setupShimmer(false)
                binding.rvChat.adapter = adapterChat
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    @SuppressLint("SimpleDateFormat")
    private fun sendMessage(sender: String, receiver: String, message: String, invoice: String) {
        refSendChat = FirebaseDatabase.getInstance().getReference(PARENT_APP).child(DATA_CHAT).child(invoice)
        val id: String? = refSendChat.push().key
        val timeStamp = SimpleDateFormat("dd-MM-yyyy HH:mm").format(Date())
        val chatModel = ChatModel(id, sender, receiver, message, timeStamp, false)
        id?.let {
            refSendChat.child(it).setValue(chatModel)
                .addOnSuccessListener {
//                    sendDeviceNotification(
//                        viewModel.name().toString(),
//                        message,
//                        viewModel.image().toString(),
//                        user?.id_notification!!
//                    )
                }
                .addOnFailureListener { }
        }
    }

    private fun seenMessage(idUser: String, invoice: String) {
        refReadChat = FirebaseDatabase.getInstance().getReference(PARENT_APP).child(DATA_CHAT).child(invoice).child(invoice)
        val query: Query = refReadChat.ref.orderByChild(CHILD_IS_SEEN).equalTo(false)
        seenListener = query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val chat = snapshot.getValue(ChatModel::class.java)
                    chat?.let {
                        if (it.receiver.equals(viewModel.getCurrentUserId()) && it.sender.equals(idUser)
                        ) {
                            snapshot.ref.child(CHILD_IS_SEEN).setValue(true)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }


//    private fun sendDeviceNotification(
//        nama: String,
//        message: String,
//        profil: String,
//        id_notif: String
//    ) {
//        Thread {
//            try {
//                val notificationContent = JSONObject(
//                    "{'include_player_ids': ['" + id_notif + "']," +
//                            "'headings': {'en': '" + nama + "'}," +
//                            "'contents': {'en': '" + message + "'}," +
//                            "'small_icon': '" + "ic_delivery_man" + "'," +
//                            "'large_icon': '" + profil + "'," +
//                            "'android_led_color': 'FF7642'," +
//                            "'collapse_id': '12345'," +
//                            "'android_accent_color': 'FF7642'}"
//                )
//                OneSignal.postNotification(
//                    notificationContent,
//                    object : PostNotificationResponseHandler {
//                        override fun onSuccess(response: JSONObject) {
//                            Log.e(
//                                "CHAT",
//                                "Success sending notification: $response"
//                            )
//                        }
//
//                        override fun onFailure(response: JSONObject) {
//                            Log.e(
//                                "CHAT",
//                                "Failure sending notification: $response"
//                            )
//                        }
//                    })
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//        }.start()
//    }

    private fun setupShimmer(state: Boolean) {
        with(binding) {
            if (state) {
                shimmer.apply {
                    startShimmer()
                    isVisible(true)
                }
            } else {
                shimmer.apply {
                    stopShimmer()
                    isVisible(false)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        seenListener?.let { refReadChat.removeEventListener(it) }
    }
}