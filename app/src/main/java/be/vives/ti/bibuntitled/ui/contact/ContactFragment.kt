package be.vives.ti.bibuntitled.ui.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.MainActivity
import be.vives.ti.bibuntitled.databinding.FragmentContactBinding

/** @author Remi Verhulst */

class ContactFragment : Fragment() {

    private lateinit var viewModel: ContactViewModel
    private lateinit var binding: FragmentContactBinding


    @Override
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentContactBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel =
            ViewModelProvider(this, ContactViewModelFactory()).get(ContactViewModel::class.java)
        Log.i("CONTACTMODEL", viewModel.campus.value.toString())

        val usercampus: String =
            (activity as MainActivity).getGebruikerActivity().campus.trim()

        viewModel.getCampus(usercampus)
        binding.viewModel = viewModel

        //naar facebook pagina
        binding.buttonFacebook.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(viewModel.campus.value?.facebook.toString().trim())
            startActivity(openURL)
        }

        //naar instagram pagina
        binding.buttonInstagram.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(viewModel.campus.value?.instagram.toString().trim())
            startActivity(openURL)
        }

        //naar pinterest pagina
        binding.buttonPinterest.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(viewModel.campus.value?.pinterest.toString().trim())
            startActivity(openURL)
        }

        //naar google maps
        binding.buttonMap.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(viewModel.campus.value?.maps.toString().trim())
            startActivity(openURL)
        }

        binding.buttonWeb.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(viewModel.campus.value?.website.toString().trim())
            startActivity(openURL)
        }

        //naar email met ontvanger ingevuld
        binding.buttonEmail.setOnClickListener {
            val ontvanger = viewModel.campus.value?.email.toString().trim()
            val mIntent = Intent(Intent.ACTION_SEND)
            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
            mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(ontvanger))

            try {
                startActivity(Intent.createChooser(mIntent, "Choose Email Client"))
            } catch (e: java.lang.Exception) {
                Toast.makeText(this.activity, e.message, Toast.LENGTH_LONG).show()
            }
        }

        //naar bel applicatie met nummer ingevuld
        binding.buttonTelefoon.setOnClickListener {
            val number: String = viewModel.campus.value?.telefoon.toString().trim()
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            startActivity(intent)
        }

        return binding.root
    }
}


