package be.vives.ti.bibuntitled

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import be.vives.ti.bibuntitled.data.*
import be.vives.ti.bibuntitled.data.game.Question
import be.vives.ti.bibuntitled.data.game.Spel
import be.vives.ti.bibuntitled.data.game.SpelInstance
import be.vives.ti.bibuntitled.data.game.Team
import be.vives.ti.bibuntitled.databinding.ActivityGameBinding
import com.google.firebase.Timestamp


class GameActivity : AppCompatActivity() {

    lateinit var groepNaam: String
    var aantalLeden: Int = 1
    lateinit var spel: Spel
    lateinit var activeTeam: Team
    lateinit var currentQuestion: Question
    lateinit var spelInstance: SpelInstance
    var inChat = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityGameBinding>(this, R.layout.activity_game)
    }

    fun getUser(): User {
        return intent.getSerializableExtra("User") as User
    }

    fun navigateToNextQuestion() {
        FirestoreRepository.instance.getNextQuestion(spel, object: FirestoreRepository.GetNextQuestionCallback {
            override fun onCallback(question: Question?) {
                if (question != null) {
                    navigateToQuestion(question)
                } else {
                    // Einde spel, navigeer naar scoreboard
                    spelInstance.Datum = Timestamp.now()
                    val time = spelInstance.Datum.seconds - activeTeam.datum.seconds
                    spelInstance.Timer = time.toInt()
                    FirestoreRepository.instance.addSpelInstance(spelInstance)
                    Log.e("EINDSPELINST",spelInstance.toString())
                    findNavController(R.id.myNavHostGameFragment).navigate(R.id.statistiekFragment)
                }
            }
        })
    }

    private fun navigateToQuestion(question: Question) {
        currentQuestion = question
        when (question.TypeVanAntwoorden) {
            0 -> findNavController(R.id.myNavHostGameFragment).navigate(R.id.selectQuestionFragment)
            1 -> findNavController(R.id.myNavHostGameFragment).navigate(R.id.checkedQuestionFragment)
            2 -> findNavController(R.id.myNavHostGameFragment).navigate(R.id.invulQuestionFragment)
        }
    }

    override fun onBackPressed() {
        if(inChat) {
            inChat = false
            navigateToQuestion(currentQuestion)
        } else if (this::spel.isInitialized && !spel.isFinished) {
            val alertDialog: AlertDialog? = this.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.yes) { _, _ ->
                        super.onBackPressed()
                    }
                    setNegativeButton(R.string.no) { dialog, _ ->
                        // User clicked cancel button
                        dialog.dismiss()
                    }


                    setTitle(R.string.confirm_leave_game_title)
                    setMessage(R.string.confirm_leave_game_text)
                }
                builder.create()
            }

            alertDialog?.show()
        } else {
            super.onBackPressed()
        }
    }
}
