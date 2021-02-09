package be.vives.ti.bibuntitled.ui.question

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import be.vives.ti.bibuntitled.GameActivity
import be.vives.ti.bibuntitled.R
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.game.Answer
import be.vives.ti.bibuntitled.databinding.FragmentQuestionSelectBinding
import kotlinx.android.synthetic.main.fragment_question_select.*


class SelectQuestionFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel
    private lateinit var binding: FragmentQuestionSelectBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gameActivity: GameActivity = activity as GameActivity
        binding = FragmentQuestionSelectBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.imageButton.setOnClickListener {
            gameActivity.findNavController(R.id.myNavHostGameFragment).navigate(R.id.chatFragment)
        }

        val spelInstance = gameActivity.spelInstance

        val question = gameActivity.currentQuestion
        viewModel = ViewModelProvider(this, QuestionViewModelFactory(question, gameActivity.spel)).get(QuestionViewModel::class.java)

        FirestoreRepository.instance.getAnswers(question, object: FirestoreRepository.GetAnswersCallback {
            override fun onCallback(answers: List<Answer>) {

                // Create radio buttons
                answers.forEach { answer ->
                    addRadioButton(answer.AntwoordTekst)
                }

                // Clicklistener mag pas gemaakt worden als answers zijn opgehaald
                binding.button.setOnClickListener {
                    val idx = radioGroup.indexOfChild(view?.findViewById<RadioButton>(radioGroup.checkedRadioButtonId))
                    when {
                        idx == -1 -> {
                            Toast.makeText(requireContext(), getString(R.string.no_answer_given), Toast.LENGTH_LONG).show()
                        }
                        answers[idx].IsEenJuistAntwoord -> {
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
                        tip.visibility == View.INVISIBLE -> {
                            // Eerste keer fout
                            tip.visibility = View.VISIBLE
                        }
                        else -> {
                            // Tweede keer fout
                            spelInstance.VragenFout.add(question.documentId)
                            gameActivity.findNavController(R.id.myNavHostGameFragment).navigate(R.id.feedbackFragment)
                        }
                    }
                }
            }

        })

        binding.viewModel = viewModel

        // Clickable <a> tags
        binding.questionTitle.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }

    private fun addRadioButton(text: String) {
        val radioButton = RadioButton(context)
        radioButton.id = View.generateViewId()
        radioButton.text = text
        radioButton.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        binding.radioGroup.addView(radioButton)
    }
}
