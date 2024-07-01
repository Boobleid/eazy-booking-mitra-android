package com.booble.eazybooking.mitra.ui.booking_cancel.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booble.eazybooking.mitra.data.model.api.booking_cancel.ConversationItem
import com.booble.eazybooking.mitra.databinding.ItemConversationLeftBinding
import com.booble.eazybooking.mitra.databinding.ItemConversationRightBinding

class ConversationAdapter(var conversationList: List<ConversationItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = conversationList.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = getItemViewType(position)
        if (itemType == MSG_TYPE_RIGHT) (holder as ChatRightViewHolder).bind(conversationList[position])
        else (holder as ChatLeftViewHolder).bind(conversationList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (conversationList[position].rightPosition == 1) MSG_TYPE_RIGHT else MSG_TYPE_LEFT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MSG_TYPE_LEFT) {
            val binding = ItemConversationLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChatLeftViewHolder(binding)
        } else {
            val binding = ItemConversationRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChatRightViewHolder(binding)
        }
    }

    class ChatLeftViewHolder(val binding: ItemConversationLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ConversationItem) {
            binding.apply {
                messageTV.text = model.pesan
            }
        }
    }

    inner class ChatRightViewHolder(val binding: ItemConversationRightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ConversationItem) {
            binding.apply {
                messageTV.text = model.pesan
            }
        }
    }

    companion object {
        private const val MSG_TYPE_LEFT = 0
        private const val MSG_TYPE_RIGHT = 1
    }
}