package be.vives.ti.bibuntitled.ui.question.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.data.game.Question

class FeedbackViewModel(question: Question) : ViewModel() {

    class Factory(val question: Question) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FeedbackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FeedbackViewModel(question) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    // question
    private val _questionItem = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _questionItem

    init {
        _questionItem.value = question
    }
}
