package be.vives.ti.bibuntitled.ui.scoreboard

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.GameActivity
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.game.SpelInstance
import be.vives.ti.bibuntitled.databinding.FragmentScoreboardBinding
import kotlinx.android.synthetic.main.fragment_scoreboard.*

class ScoreboardFragment : Fragment() {

    private lateinit var viewModel: ScoreboardViewModel
    private lateinit var binding: FragmentScoreboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoreboardBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, ScoreboardViewModelFactory(FirestoreRepository())).get(
            ScoreboardViewModel::class.java
        )
        binding.viewModel = viewModel

        getGameInstances()

        (activity as GameActivity).spel.isFinished = true


        binding.home.setOnClickListener {
            activity?.onBackPressed()
        }


        return binding.root
    }

    private fun getGameInstances() {
        FirestoreRepository().getGameInstances(object :
            FirestoreRepository.GetGameInstancesCallback {
            override fun onCallback(spelInstances: List<SpelInstance>) {
                if (spelInstances.isNotEmpty()) {
                    val sbn = StringBuilder()
                    val sbt = StringBuilder()
                    val sbs = StringBuilder()
                    var number = 0
                    val filteredList = spelInstances.filter { it.SpelCode == (activity as GameActivity).spelInstance.SpelCode }
                    val sortedList = filteredList.sortedByDescending { it.Score }
                    sortedList.forEach { spelInstance: SpelInstance ->
                        val team = spelInstance.TeamNaam.trim()
                        val score = spelInstance.Score
                        number += 1
                        sbn.append("[$number]\n")
                        sbt.append("$team:\n")
                        sbs.append("$score\n")
                    }
                    tvRanking.text = sbn.toString()
                    tvTeamnaam.text = sbt.toString()
                    tvScore.text = sbs.toString()

                } else {
                    Log.e(ContentValues.TAG, "geen spel instanties gevonden")
                }
            }
        })
    }

}