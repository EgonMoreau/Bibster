package be.vives.ti.bibuntitled.ui.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.vives.ti.bibuntitled.data.game.Spel
import be.vives.ti.bibuntitled.data.game.Question

class QuestionViewModel(question: Question, spel: Spel) : ViewModel() {

    // question
    private val _questionItem = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _questionItem

    // spel
    private val _spel = MutableLiveData<Spel>()
    val spel: LiveData<Spel>
        get() = _spel

    init {
        _questionItem.value = question
        _spel.value = spel
    }
}