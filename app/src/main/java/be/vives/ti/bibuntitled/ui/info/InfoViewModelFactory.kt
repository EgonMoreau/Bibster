package be.vives.ti.bibuntitled.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.ui.info.InfoViewModel


/**
 * @author Moreau Egon
 */
class InfoViewModelFactory : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InfoViewModel::class.java)) {
            return InfoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}