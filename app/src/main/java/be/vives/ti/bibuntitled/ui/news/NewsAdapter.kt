package be.vives.ti.bibuntitled.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.vives.ti.bibuntitled.data.NewsItem
import be.vives.ti.bibuntitled.databinding.ListNewsItemBinding

/** @author Joshua Tack */
class NewsAdapter(private val clickListener: NewsItemListener) :
    ListAdapter<NewsItem, NewsAdapter.NewsItemViewHolder>(NewsItemDiffCallback) {


    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(ListNewsItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    @Override
    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class NewsItemViewHolder(val binding: ListNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            newsItem: NewsItem,
            clickListener: NewsItemListener
        ) {
            binding.newsItem = newsItem
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    companion object NewsItemDiffCallback : DiffUtil.ItemCallback<NewsItem>() {

        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem === newItem
        }


        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem == newItem
        }
    }

    class NewsItemListener(val clickListener: (newsItem: NewsItem) -> Unit) {
        fun onClick(newsItem: NewsItem) = clickListener(newsItem)
    }
}