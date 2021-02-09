package be.vives.ti.bibuntitled.ui.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import be.vives.ti.bibuntitled.databinding.FragmentInfoBinding


/**
 * @author Moreau Egon
 */
class InfoFragment : Fragment() {

    private lateinit var viewModel: InfoViewModel
    private lateinit var binding: FragmentInfoBinding
    private lateinit var navController: NavController;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        InfoViewModelFactory()

        viewModel = ViewModelProvider(this, InfoViewModelFactory()).get(InfoViewModel::class.java)
        Log.i("INFOINFO", viewModel.infoItem.value.toString())

        binding.viewModel = viewModel

        binding.vivesLoginButton.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.vives.be/nl/tools/account")
            startActivity(openURL)
        }

        binding.mailboxButton.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.vives.be/nl/tools/webmail")
            startActivity(openURL)
        }

        binding.toledoButton.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.vives.be/nl/tools/toledo")
            startActivity(openURL)
        }

        binding.limoButton.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.vives.be/nl/tools/limo")
            startActivity(openURL)
        }


        return binding.root

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }
}

