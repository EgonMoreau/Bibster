package be.vives.ti.bibuntitled.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import be.vives.ti.bibuntitled.MainActivity
import be.vives.ti.bibuntitled.R
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.databinding.FragmentLoginBinding
import com.afollestad.vvalidator.form
import com.afollestad.vvalidator.util.hide
import com.afollestad.vvalidator.util.show
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            LoginViewModelFactory(
                FirestoreRepository.instance
            )
        ).get(LoginViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val email = sharedPref?.getString("email", null)
        val password = sharedPref?.getString("password", null)

        if (email != null && password != null) {
            binding.progressBar.show()
            (activity as MainActivity).login(email, password, object: MainActivity.GetLoginCallback {
                override fun onCallback(successful: Boolean) {
                    binding.progressBar.hide()
                }
            })
        }


        binding.registerBtn.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.progressBar.hide()
        form {
            input(binding.gebruikersnaam) {
                isNotEmpty().description("Veld mag niet leeg zijn.")
                isEmail().description("Vul een geldig emailadres in.")
            }
            input(binding.wachtwoord) {
                isNotEmpty().description("Veld mag niet leeg zijn.")
            }
            submitWith(binding.loginBtn) {
                binding.tvError.hide()
                binding.progressBar.show()
                val gebruikersnaam = gebruikersnaam.text.toString()
                val wachtwoord = wachtwoord.text.toString()
                (activity as MainActivity).login(
                    gebruikersnaam,
                    wachtwoord,
                    object : MainActivity.GetLoginCallback {
                        override fun onCallback(successful: Boolean) {
                            if (!successful)
                                binding.tvError.show()
                            binding.tvError.text =
                                "Gebruikersnaam en/of wachtwoord zijn incorrect"
                            binding.progressBar.hide()
                        }
                    })
            }
        }
        return binding.root
    }

}