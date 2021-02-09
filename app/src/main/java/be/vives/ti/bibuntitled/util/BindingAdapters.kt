package be.vives.ti.bibuntitled.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import be.vives.ti.bibuntitled.data.ChatBericht
import be.vives.ti.bibuntitled.data.NewsItem
import be.vives.ti.bibuntitled.ui.chat.ChatAdapter
import be.vives.ti.bibuntitled.ui.news.NewsAdapter

/** @author Joshua Tack */


@BindingAdapter("listData")
fun bindNewsItemsRecyclerView(recyclerView: RecyclerView, data: List<NewsItem>?) {
    val adapter = recyclerView.adapter as NewsAdapter
    adapter.submitList(data)
}
@BindingAdapter("berichtenData")
fun bindChatBerichtenRecyclerView(recyclerView: RecyclerView, data: List<ChatBericht>?){
    val adapter = recyclerView.adapter as ChatAdapter
    adapter.submitList(data)
}

