package be.vives.ti.bibuntitled.ui.chat

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import be.vives.ti.bibuntitled.data.ChatBericht
import be.vives.ti.bibuntitled.data.FirestoreRepository

class ChatViewModel : ViewModel() {

    private val _chatBerichten = MutableLiveData<List<ChatBericht>>()
    val chatBerichten: LiveData<List<ChatBericht>>
        get() = _chatBerichten


    fun addToRecycleView(bericht: ChatBericht) {
        _chatBerichten.value = (_chatBerichten.value as List<ChatBericht>).plus(bericht)
    }
    init {
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stuurBericht(
        bericht: ChatBericht,
        groep: String,
        callback: FirestoreRepository.StuurBerichtCallback
    ) {
        FirestoreRepository.instance.stuurBericht(bericht, groep, callback)
    }

    fun getBerichten(groep: String, callback: FirestoreRepository.GetChatBerichtenCallback) {
        FirestoreRepository.instance.getBerichten(
            groep,
            object : FirestoreRepository.GetChatBerichtenCallback {
                override fun onCallback(chatBerichten: List<ChatBericht>) {
                    _chatBerichten.value = chatBerichten
                    callback.onCallback(chatBerichten)
                }
            })
    }

    private fun setupAdapter(data: ArrayList<ChatBericht>, context: Context) {
        val linearLayoutManager = LinearLayoutManager(context)
    }

}