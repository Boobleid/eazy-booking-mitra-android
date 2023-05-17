package com.booble.reservasi.mitra.back_up.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.data.model.api.firebase.ChatModel
import com.booble.reservasi.mitra.databinding.ItemContainerChatLeftBinding
import com.booble.reservasi.mitra.databinding.ItemContainerChatRightBinding

class ChatAdapter(
    var mContext: Context,
    var listChat: List<ChatModel>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val MSG_TYPE_LEFT = 0
        private const val MSG_TYPE_RIGHT = 1
    }

    var myId: String? = null

    override fun getItemCount(): Int = listChat.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = getItemViewType(position)
        if (itemType == MSG_TYPE_RIGHT) (holder as ChatRightViewHolder).bind(listChat[position], listChat, mContext)
        else (holder as ChatLeftViewHolder).bind(listChat[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (listChat[position].sender.equals(myId)) MSG_TYPE_RIGHT else MSG_TYPE_LEFT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MSG_TYPE_LEFT) {
            val binding = ItemContainerChatLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChatLeftViewHolder(binding)
        } else {
            val binding = ItemContainerChatRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChatRightViewHolder(binding)
        }
    }

    class ChatLeftViewHolder(val binding: ItemContainerChatLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ChatModel) {
            binding.apply {
                tvTimestamp.text = model.timestamp
                tvMessage.text = model.message
            }
        }
    }

    class ChatRightViewHolder(val binding: ItemContainerChatRightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ChatModel, listChat: List<ChatModel>, context: Context) {
            binding.apply {
                tvMessage.text = model.message
                tvTimestamp.text = model.timestamp
                if (adapterPosition == listChat.size - 1) {
                    if (model.are_is_seen) binding.tvSeen.text = context.getString(R.string.dilihat)
                    else binding.tvSeen.text = context.getString(R.string.terkirim)
                } else binding.tvSeen.visibility = View.GONE
            }
        }
    }
}