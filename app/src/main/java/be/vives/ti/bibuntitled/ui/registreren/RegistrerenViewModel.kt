package be.vives.ti.bibuntitled.ui.registreren
import androidx.lifecycle.ViewModel
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.User

class RegistrerenViewModel(private val firestoreRepository: FirestoreRepository)
    : ViewModel() {

    fun addUser(user: User): User{
        return firestoreRepository.addUser(user)
    }
    fun userExists(gebruikersnaam: String): Boolean{
        //return firestoreRepository.userExists(gebruikersnaam)
        return false
    }
}