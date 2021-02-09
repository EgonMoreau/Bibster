package be.vives.ti.bibuntitled.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import be.vives.ti.bibuntitled.GameActivity
import be.vives.ti.bibuntitled.MainActivity
import be.vives.ti.bibuntitled.R
import be.vives.ti.bibuntitled.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = Navigation.findNavController(requireActivity(), R.id.myNavHostFragment)

        val binding = DataBindingUtil.inflate<FragmentDashboardBinding>(
            inflater,
            R.layout.fragment_dashboard,
            container,
            false
        )

        binding.btnInfo.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_dashboardFragment_to_infoFragment)
        }

        binding.btnContact.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_dashboardFragment_to_contactFragment)

        }

        binding.btnGame.setOnClickListener {
            val intent = Intent((activity as MainActivity), GameActivity::class.java)
            val user = (activity as MainActivity).getGebruikerActivity()
            intent.putExtra("User", user)
            startActivity(intent)
        }

        binding.btnNews.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_dashboardFragment_to_newsFragment)
        }

        binding.btnPlattegrond.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_dashboardFragment_to_plattegrondFragment)
        }

        return binding.root
    }
}
