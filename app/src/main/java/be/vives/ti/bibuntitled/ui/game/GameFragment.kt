package be.vives.ti.bibuntitled.ui.game

import android.os.Bundle
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
import be.vives.ti.bibuntitled.data.game.Spel
import be.vives.ti.bibuntitled.databinding.FragmentGameBinding
import kotlinx.android.synthetic.main.fragment_game.*


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            GameViewModelFactory(
                FirestoreRepository.instance
            )
        ).get(GameViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gameActivity: GameActivity = activity as GameActivity
        binding = FragmentGameBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        binding.btnNext.setOnClickListener {
            val spelCode = spelCode.text.toString()

            FirestoreRepository.instance.getSpel(spelCode, object : FirestoreRepository.GetSpelCallback {
                override fun onCallback(spel: Spel?) {
                    if (spel != null) {
                        Log.d("GETSPEL", spel.toString())
                        gameActivity.spel = spel
                        view?.findNavController()
                            ?.navigate(R.id.action_GameFragment_to_TeamFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.incorrect_code),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
        return binding.root
    }
}
