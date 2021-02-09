package be.vives.ti.bibuntitled.ui.statistiek

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.vives.ti.bibuntitled.data.game.SpelInstance
import be.vives.ti.bibuntitled.ui.question.feedback.FeedbackViewModel

/** @author Remi Verhulst */

class StatistiekViewModel : ViewModel() {
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StatistiekViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StatistiekViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    private val _statistiekItem = MutableLiveData<SpelInstance>()
    val statistiek: LiveData<SpelInstance>
        get() = _statistiekItem

    private var _minutes = MutableLiveData<String>()
    val timerMinutes: LiveData<String>
        get() = _minutes

    private var _score = MutableLiveData<String>()
    val score: LiveData<String>
        get() = _score

    private var _atljuist = MutableLiveData<String>()
    val atljuist: LiveData<String>
        get() = _atljuist

    private var _atlfout = MutableLiveData<String>()
    val atlfout: LiveData<String>
        get() = _atlfout

    private var _atltip = MutableLiveData<String>()
    val atltip: LiveData<String>
        get() = _atltip

    private var _sec = MutableLiveData<String>()
    val timerSec: LiveData<String>
        get() = _sec

    fun setStatistiek(spelInstance: SpelInstance){
        _statistiekItem.value = spelInstance
    }

    fun setVelden(spelInstance: SpelInstance){
        _score.value = spelInstance.Score.toString()
        _atljuist.value = spelInstance.VragenJuist.size.toString()
        _atlfout.value = spelInstance.VragenFout.size.toString()
        _atltip.value = spelInstance.VragenTip.size.toString()
    }

    fun setTimer(timer: Int){
        _minutes.value = (timer/60).toString()
        _sec.value = (timer % 60).toString()
    }




}