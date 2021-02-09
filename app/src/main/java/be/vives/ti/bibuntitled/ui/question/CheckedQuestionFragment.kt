package be.vives.ti.bibuntitled.ui.question

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import be.vives.ti.bibuntitled.GameActivity
import be.vives.ti.bibuntitled.R
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.game.Answer
import be.vives.ti.bibuntitled.databinding.FragmentQuestionCheckedBinding
import kotlinx.android.synthetic.main.fragment_question_checked.*


class CheckedQuestionFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel
    private lateinit var binding: FragmentQuestionCheckedBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gameActivity: GameActivity = activity as GameActivity
        binding = FragmentQuestionCheckedBinding.inflate(inflater)
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
                    addCheckBox(answer.AntwoordTekst)
                }

                // Clicklistener mag pas gemaakt worden als answers zijn opgehaald
                binding.button.setOnClickListener {
                    var heeftFout = false
                    var nietsChecked = true

                    var idx = 0
                    binding.checkboxGroup.forEach { checkbox ->
                        if ((checkbox as CheckBox).isChecked) {
                            nietsChecked = false
                            if (answers[idx].IsEenJuistAntwoord) {
                                // Correct antwoord aangeduid
                                binding.checkboxGroup[idx].isEnabled = false
                            } else {
                                // Fout antwoord aangeduid
                                heeftFout = true
                            }
                        } else if (answers[idx].IsEenJuistAntwoord) {
                            // Correct antwoord niet aangeduid
                            heeftFout = true
                        }

                        idx++
                    }

                    if (nietsChecked) {
                        Toast.makeText(requireContext(), getString(R.string.no_answer_given), Toast.LENGTH_LONG).show()
                    } else {
                        if(!heeftFout) {
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
                        } else if (heeftFout && tip.visibility == View.INVISIBLE) {
                            // 1ste keer fout
                            tip.visibility = View.VISIBLE
                        } else if (heeftFout && tip.visibility == View.VISIBLE) {
                            // 2de keer fout
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

    private fun addCheckBox(text: String) {
        val checkBox = CheckBox(context)
        checkBox.id = View.generateViewId()
        checkBox.text = text
        checkBox.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        checkboxGroup.addView(checkBox)
    }
}

