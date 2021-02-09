package be.vives.ti.bibuntitled.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/** @author Joshua Tack **/
class NewsViewModelFactory : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}