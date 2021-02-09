package be.vives.ti.bibuntitled.ui.team

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.game.Team
import be.vives.ti.bibuntitled.data.User

/** @author Remi Verhulst */

class TeamViewModel(private val firestoreRepository: FirestoreRepository)
    : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private var team : Team =
        Team()
    private var user: User = User()
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTeam(team: Team): Team {
        return firestoreRepository.addTeam(team)
    }

}
