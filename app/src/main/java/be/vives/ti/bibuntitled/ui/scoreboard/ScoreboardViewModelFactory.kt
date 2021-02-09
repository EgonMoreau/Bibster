package be.vives.ti.bibuntitled.ui.scoreboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.data.FirestoreRepository

class ScoreboardViewModelFactory (private val firestoreRepository: FirestoreRepository)
    : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScoreboardViewModel(
            firestoreRepository
        ) as T
    }
}
