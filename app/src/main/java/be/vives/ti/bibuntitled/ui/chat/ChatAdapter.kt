package be.vives.ti.bibuntitled.ui.chat

import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.vives.ti.bibuntitled.data.ChatBericht
import be.vives.ti.bibuntitled.databinding.ChatVanRijBinding

class ChatAdapter : ListAdapter<ChatBericht, ChatAdapter.ChatBerichtViewHolder>(ChatBerichtDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatBerichtViewHolder {
        val r = parent.context.resources
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 210F, r.displayMetrics)
        return ChatBerichtViewHolder(ChatVanRijBinding.inflate(LayoutInflater.from(parent.context)), px.toInt())

    }


    override fun onBindViewHolder(holder: ChatBerichtViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }


    class ChatBerichtViewHolder(var binding: ChatVanRijBinding, var px: Int) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatbericht: ChatBericht) {
            if(chatbericht.sender=="Beheerder") {
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(8, 2, 0, 2)
                binding.chatBericht = chatbericht
                params.marginStart = px
                binding.textviewFromRow.setBackgroundColor(Color.GREEN)
                binding.textviewFromRow.layoutParams = params
                binding.executePendingBindings()
            }else{
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(0, 8, 8, 2)
                params.marginEnd = px
                binding.chatBericht = chatbericht
                binding.textviewFromRow.setBackgroundColor(Color.RED)
                binding.textviewFromRow.layoutParams = params
                binding.executePendingBindings()
            }
        }


    }



   object ChatBerichtDiffCallback : DiffUtil.ItemCallback<ChatBericht>() {

        override fun areItemsTheSame(oldItem: ChatBericht, newItem: ChatBericht): Boolean {

            return oldItem === newItem
        }


        override fun areContentsTheSame(oldItem: ChatBericht, newItem: ChatBericht): Boolean {
            return oldItem == newItem
        }
    }

    class ChatBerichtListener
}