package be.vives.ti.bibuntitled.ui.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/** @author Remi Verhulst */

@Suppress("UNCHECKED_CAST")
class ContactViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}