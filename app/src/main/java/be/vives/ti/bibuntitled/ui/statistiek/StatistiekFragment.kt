package be.vives.ti.bibuntitled.ui.statistiek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import be.vives.ti.bibuntitled.GameActivity
import be.vives.ti.bibuntitled.R
import be.vives.ti.bibuntitled.databinding.FragmentFeedbackBinding
import be.vives.ti.bibuntitled.databinding.FragmentStatistiekBinding
import be.vives.ti.bibuntitled.ui.question.feedback.FeedbackViewModel

/** @author Remi Verhulst */

class StatistiekFragment : Fragment() {

    private lateinit var viewModel: StatistiekViewModel
    private lateinit var binding: FragmentStatistiekBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gameActivity: GameActivity = activity as GameActivity
        binding = FragmentStatistiekBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, StatistiekViewModel.Factory()).get(StatistiekViewModel::class.java)
        viewModel.setStatistiek(gameActivity.spelInstance)
        viewModel.setVelden(gameActivity.spelInstance)
        viewModel.setTimer(gameActivity.spelInstance.Timer)
        binding.viewModel = viewModel

        binding.home.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.scorebord.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_statistiekFragment_to_scoreboardFragment)
        }



        return binding.root
    }
}
