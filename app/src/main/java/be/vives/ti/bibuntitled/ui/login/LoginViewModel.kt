package be.vives.ti.bibuntitled.ui.login
import androidx.lifecycle.ViewModel
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class LoginViewModel(private val firestoreRepository: FirestoreRepository)
    : ViewModel() {
    /*fun userExists(gebruikersnaam: String): Boolean{
        return firestoreRepository.userExists(gebruikersnaam)
    }*/
    fun getUser(gebruikersnaam: String): User{
    //    return firestoreRepository.getUser(gebruikersnaam)
        return User()

    }
}