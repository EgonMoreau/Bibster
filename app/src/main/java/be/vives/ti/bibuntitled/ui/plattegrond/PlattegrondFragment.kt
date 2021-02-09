package be.vives.ti.bibuntitled.ui.plattegrond

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.vives.ti.bibuntitled.MainActivity
import be.vives.ti.bibuntitled.R
import kotlinx.android.synthetic.main.fragment_plattegrond.view.*


class PlattegrondFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.fragment_plattegrond, container, false)
        val stadNu: String = (getActivity() as MainActivity).getGebruikerActivity().campus
        when (stadNu) {
            "Kortrijk" -> {
                binding.photo_view.setImageResource(R.drawable.kortrijk)
                true
            }
            "Brugge" -> {
                binding.photo_view.setImageResource(R.drawable.brugge)
                true
            }
            else -> throw Exception("Errorbij switchen plattegrond")

        }
        binding.stadPlat.text = stadNu
        return binding
    }


}

