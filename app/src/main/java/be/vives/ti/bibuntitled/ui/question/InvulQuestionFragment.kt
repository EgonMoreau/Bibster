package be.vives.ti.bibuntitled.ui.question

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import be.vives.ti.bibuntitled.GameActivity
import be.vives.ti.bibuntitled.R
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.game.Answer
import be.vives.ti.bibuntitled.data.game.Question
import be.vives.ti.bibuntitled.databinding.FragmentQuestionInvulBinding
import kotlinx.android.synthetic.main.fragment_question_invul.*
import java.util.*


class InvulQuestionFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel
    private lateinit var binding: FragmentQuestionInvulBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gameActivity: GameActivity = activity as GameActivity
        binding = FragmentQuestionInvulBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val spelInstance = gameActivity.spelInstance
        val question: Question = gameActivity.currentQuestion
        viewModel = ViewModelProvider(this, QuestionViewModelFactory(question, gameActivity.spel)).get(QuestionViewModel::class.java)

        Log.i("INPUTQUESTION", viewModel.question.value.toString())
        binding.viewModel = viewModel
        binding.imageButton.setOnClickListener(View.OnClickListener {
            gameActivity.findNavController(R.id.myNavHostGameFragment).navigate(R.id.chatFragment)
        })
        FirestoreRepository.instance.getAnswers(question, object: FirestoreRepository.GetAnswersCallback {
            override fun onCallback(answers: List<Answer>) {

                // Clicklistener mag pas gemaakt worden als answers zijn opgehaald
                binding.button.setOnClickListener {
                    // Trim and lowercase userinput
                    val antw = antwoord.text.toString().trim().toLowerCase(Locale.getDefault())

                    if (antw.isEmpty()) {
                        Toast.makeText(requireContext(), getString(R.string.no_answer_given), Toast.LENGTH_LONG).show()
                    } else {
                        answers.forEach { answer ->
                            val answerTxt = answer.AntwoordTekst.trim().toLowerCase(Locale.ROOT)

                            if (answer.IsEenJuistAntwoord && antw == answerTxt) {
                                // Juist

                                if (tip.visibility == View.INVISIBLE) {
                                    spelInstance.VragenJuist.add(question.documentId)
                                    question.pointsAwarded = 2
                                    spelInstance.Score += 2
                                } else {
                                    spelInstance.VragenTip.add(question.documentId)
                                    question.pointsAwarded = 1
                                    spelInstance.Score += 1
                                }
                                gameActivity.findNavController(R.id.myNavHostGameFragment).navigate(R.id.feedbackFragment)
                            }
                        }

                        if (tip.visibility == View.INVISIBLE) {
                            // Eerste keer fout
                            tip.visibility = View.VISIBLE
                        } else {
                            // Tweede keer fout
                            spelInstance.VragenFout.add(question.documentId)
                            gameActivity.findNavController(R.id.myNavHostGameFragment).navigate(R.id.feedbackFragment)
                        }
                    }
                }
            }
        })

        // Clickable <a> tags
        binding.questionTitle.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }
}
