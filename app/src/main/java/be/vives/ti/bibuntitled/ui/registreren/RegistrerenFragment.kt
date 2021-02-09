package be.vives.ti.bibuntitled.ui.registreren

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.MainActivity
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.User
import be.vives.ti.bibuntitled.databinding.FragmentRegistrerenBinding
import com.afollestad.vvalidator.form
import kotlinx.android.synthetic.main.fragment_registreren.*

class RegistrerenFragmentFragment : Fragment() {
    private lateinit var binding: FragmentRegistrerenBinding
    private val viewModel: RegistrerenViewModel by lazy {
        ViewModelProvider(
            this,
            RegistrerenViewModelFactory(
                FirestoreRepository.instance
            )
        ).get(RegistrerenViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrerenBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        form {
            input(binding.email) {
                isNotEmpty().description("Veld mag niet leeg zijn.")
                isEmail()
            }
            input(binding.wachtwoord) {
                isNotEmpty().description("Veld mag niet leeg zijn.")
                length().greaterThan(5).description("Wachtwoord moet langer zijn dan 5.")
            }
            input(binding.bevestigWachtwoord) {
                isNotEmpty().description("Veld mag niet leeg zijn.")
                length().greaterThan(5).description("Wachtwoord moet langer zijn dan 5.")
            }
            input(binding.voornaam) { isNotEmpty().description("Veld mag niet leeg zijn.") }
            input(binding.achternaam) { isNotEmpty().description("Veld mag niet leeg zijn.") }
            submitWith(binding.registerBtn) { result ->
                val gebruikersnaam = email.text.toString()
                val wachtwoord = wachtwoord.text.toString()
                val bevestigwachtwoord = bevestigWachtwoord.text.toString()
                val richting = richting.selectedItem.toString()
                val campus = campus.selectedItem.toString()
                val voornaam = voornaam.text.toString()
                val achternaam = achternaam.text.toString()
                if (bevestigwachtwoord == wachtwoord) {
                    (activity as MainActivity).createAccount(
                        User(
                            "",
                            gebruikersnaam,
                            voornaam,
                            achternaam,
                            campus,
                            richting
                        ), wachtwoord, view
                    )
                } else {
                    tvError.text = "Wachtwoorden zijn niet gelijk"
                }

            }
        }

        return binding.root
    }

}