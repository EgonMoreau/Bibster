package be.vives.ti.bibuntitled.ui.team;

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.data.FirestoreRepository
import kotlin.Suppress

/** @author Remi Verhulst */

class TeamViewModelFactory (private val firestoreRepository: FirestoreRepository)
    : ViewModelProvider.NewInstanceFactory(){
    @SuppressLint("NewApi")
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TeamViewModel(
            firestoreRepository
        ) as T
    }
}