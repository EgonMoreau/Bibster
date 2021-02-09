package be.vives.ti.bibuntitled.ui.team

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.GameActivity
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.game.Spel
import be.vives.ti.bibuntitled.data.game.SpelInstance
import be.vives.ti.bibuntitled.data.game.Team
import be.vives.ti.bibuntitled.databinding.FragmentTeamBinding
import com.afollestad.vvalidator.form
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_team.*
import java.time.LocalDateTime
import java.time.ZoneId

/** @author Remi Verhulst */

class TeamFragment : Fragment() {

    private lateinit var binding: FragmentTeamBinding
    private val viewModel: TeamViewModel by lazy {
        ViewModelProvider(
            this,
            TeamViewModelFactory(
                FirestoreRepository.instance
            )
        ).get(TeamViewModel::class.java)
    }
    private lateinit var gameActivity: GameActivity

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gameActivity = activity as GameActivity
        binding = FragmentTeamBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        form {
            input(binding.teamName) {
                isNotEmpty().description("Veld mag niet leeg zijn.")
            }
            submitWith(binding.buttonTeamSubmit) {
                createTeam()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamMembers.minValue = 1
        teamMembers.maxValue = 10
        teamMembers.wrapSelectorWheel = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createTeam() {
        val tz = ZoneId.systemDefault()
        val localDateTime = LocalDateTime.now()
        val seconds = localDateTime.atZone(tz).toEpochSecond()
        val nanos = localDateTime.nano

        val richting: String = gameActivity.getUser().richting
        val campus: String = gameActivity.getUser().campus
        val datum: Timestamp = Timestamp(seconds, nanos)
        val teamnaam: String = teamName.text.toString()
        val aantal: Int = teamMembers.value
        val activeSpel: Spel = gameActivity.spel

        viewModel.addTeam(
            Team(
                teamnaam,
                aantal,
                datum,
                campus,
                richting
            )
        )
        gameActivity.activeTeam = Team(
            teamnaam,
            aantal,
            datum,
            campus,
            richting
        )
        gameActivity.spelInstance =
            SpelInstance(
                aantal,
                activeSpel.Vragen.size,
                campus,
                Timestamp(0, 0),
                0,
                activeSpel.Code, activeSpel.Studiegebied, teamnaam,
                0,
                mutableListOf(),
                mutableListOf(),
                mutableListOf()
            )

        gameActivity.groepNaam = teamName.text.toString()
        gameActivity.aantalLeden = aantal
        gameActivity.navigateToNextQuestion()
    }
}