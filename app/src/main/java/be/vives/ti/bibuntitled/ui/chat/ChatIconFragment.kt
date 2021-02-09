package be.vives.ti.bibuntitled.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.vives.ti.bibuntitled.GameActivity
import be.vives.ti.bibuntitled.databinding.FragmentChatBinding

class ChatIconFragment : Fragment(){
    private lateinit var viewModel: ChatIconViewModel
    private lateinit var binding: FragmentChatBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChatBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.groep = (activity as GameActivity).groepNaam

        /*val viewModelFactory = ChatViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
        viewModel.getBerichten((activity as GameActivity).getGroep())
        binding.viewModel = viewModel

        binding.verzenden.setOnClickListener {
            if(verzenden.text.toString()!=""){
                val tekst = nieuwBericht.text.toString()
                nieuwBericht.text= Editable.Factory.getInstance().newEditable("")
                var bericht = ChatBericht()
                bericht.message = tekst
                bericht.sender = binding.groep.toString()
                bericht.date= Date()

                viewModel.stuurBericht(bericht, bericht.sender)
            }
        }

        binding.chatList.adapter = ChatAdapter()*/


        return binding.root
    }
}