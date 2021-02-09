package be.vives.ti.bibuntitled.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.data.game.Spel
import be.vives.ti.bibuntitled.data.game.Question

class QuestionViewModelFactory(private val question: Question, private val spel: Spel): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(QuestionViewModel::class.java)){
            return QuestionViewModel(question, spel) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}
