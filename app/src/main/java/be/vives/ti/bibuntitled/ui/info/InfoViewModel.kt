package be.vives.ti.bibuntitled.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.InfoItem

/**
 * @author Moreau Egon
 */
class InfoViewModel : ViewModel() {

    private val _infoItem = MutableLiveData<InfoItem>()
    val infoItem: LiveData<InfoItem>
        get() = _infoItem


    init {
        getInfoItem()
    }

    private fun getInfoItem() {
        FirestoreRepository.instance.getInfoItems(object: FirestoreRepository.GetInfoItemsCallback {
            override fun onCallback(infoItem: InfoItem) {
                _infoItem.value = infoItem
            }
        })
    }
}
