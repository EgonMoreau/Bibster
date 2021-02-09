package be.vives.ti.bibuntitled.ui.question.feedback

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.GameActivity
import be.vives.ti.bibuntitled.R
import be.vives.ti.bibuntitled.databinding.FragmentFeedbackBinding


class FeedbackFragment : Fragment() {

    private lateinit var viewModel: FeedbackViewModel
    private lateinit var binding: FragmentFeedbackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedbackBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val gameActivity = activity as GameActivity
        val question = gameActivity.currentQuestion

        viewModel = ViewModelProvider(this, FeedbackViewModel.Factory(question)).get(FeedbackViewModel::class.java)
        binding.viewModel = viewModel

        if (question.pointsAwarded > 0) {
            binding.txtCorrect.text = getString(R.string.correct)
            binding.txtCorrect.setTextColor(resources.getColor(R.color.correct))
        } else {
            binding.txtCorrect.text = getString(R.string.incorrect)
            binding.txtCorrect.setTextColor(resources.getColor(R.color.red))
        }

        binding.button.setOnClickListener {
            gameActivity.navigateToNextQuestion()
        }

        // Clickable <a> tags
        binding.feedback.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }
}
