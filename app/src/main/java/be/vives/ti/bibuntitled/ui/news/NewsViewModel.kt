package be.vives.ti.bibuntitled.ui.news

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.NewsItem

/** @author Joshua Tack **/
class NewsViewModel : ViewModel() {

    private val _newsItems = MutableLiveData<List<NewsItem>>()
    val newsItems: LiveData<List<NewsItem>>
        get() = _newsItems

    init {
        getNewsItems()
    }

    /**
     * Vraag news items op uit de [FirestoreRepository]*/
    private fun getNewsItems() {
        FirestoreRepository.instance.getNewsItems(object: FirestoreRepository.GetNewsItemsCallback {
            override fun onCallback(newsItems: List<NewsItem>) {
                if (newsItems.isNotEmpty()) {
                    _newsItems.value = newsItems
                } else {
                    Log.e(TAG, "Geen news items")
                }
            }
        })

    }

}