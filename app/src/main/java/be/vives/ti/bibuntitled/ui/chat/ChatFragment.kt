package be.vives.ti.bibuntitled.ui.chat

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.GameActivity
import be.vives.ti.bibuntitled.data.ChatBericht
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.databinding.FragmentChatBinding
import com.afollestad.vvalidator.util.hide
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*

class ChatFragment : Fragment() {
    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: FragmentChatBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gameActivity = activity as GameActivity
        gameActivity.inChat = true
        binding = FragmentChatBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.groep = gameActivity.groepNaam

        val viewModelFactory = ChatViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChatViewModel::class.java)
        viewModel.getBerichten(
            gameActivity.groepNaam,
            object : FirestoreRepository.GetChatBerichtenCallback {
                override fun onCallback(chatBerichten: List<ChatBericht>) {
                    binding.progressbar.hide()
                }
            })
        binding.viewModel = viewModel

        binding.verzenden.setOnClickListener {
            if (!nieuwBericht.text.isBlank()) {
                val tekst = nieuwBericht.text.toString()
                nieuwBericht.text = Editable.Factory.getInstance().newEditable("")
                val bericht = ChatBericht()
                bericht.message = tekst
                bericht.sender = binding.groep.toString()
                bericht.date = Date()

                viewModel.stuurBericht(
                    bericht,
                    bericht.sender,
                    object : FirestoreRepository.StuurBerichtCallback {
                        override fun onCallback(success: Boolean) {
                            binding.chatList.scrollToPosition(binding.chatList.adapter!!.itemCount - 1)
                        }
                    })
            }
        }
        binding.nieuwBericht.setOnKeyListener(View.OnKeyListener{ v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (!nieuwBericht.text.isBlank()) {
                    val tekst = nieuwBericht.text.toString()
                    nieuwBericht.text = Editable.Factory.getInstance().newEditable("")
                    val bericht = ChatBericht()
                    bericht.message = tekst
                    bericht.sender = binding.groep.toString()
                    bericht.date = Date()

                    viewModel.stuurBericht(
                        bericht,
                        bericht.sender,
                        object : FirestoreRepository.StuurBerichtCallback {
                            override fun onCallback(success: Boolean) {
                                binding.chatList.scrollToPosition(binding.chatList.adapter!!.itemCount - 1)
                            }
                        })
                }
                return@OnKeyListener true
            }
            false
        })
        binding.chatList.adapter = ChatAdapter()
        return binding.root
    }

}


