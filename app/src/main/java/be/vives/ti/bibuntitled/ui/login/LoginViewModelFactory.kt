package be.vives.ti.bibuntitled.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.data.FirestoreRepository

class LoginViewModelFactory (private val firestoreRepository: FirestoreRepository)
    : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(
            firestoreRepository
        ) as T
    }
}