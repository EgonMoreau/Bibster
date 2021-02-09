package be.vives.ti.bibuntitled.ui.contact

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.vives.ti.bibuntitled.data.Campus
import be.vives.ti.bibuntitled.data.FirestoreRepository

/** @author Remi Verhulst */

class ContactViewModel : ViewModel() {

    private val _contactItem = MutableLiveData<Campus>()
    val campus: LiveData<Campus>
    get() = _contactItem


    fun getCampus(campusid : String) {
        var item: Campus?
        item = null
        FirestoreRepository.instance.getCampus().whereEqualTo("campus",campusid).get()
            .addOnSuccessListener { document ->
                if (document != null ) {
                    val campusen = document.toObjects(Campus::class.java)
                    item = campusen[0]
                    _contactItem.value = item
                    Log.d("CAMPUS",item.toString())
                }
                else {
                    Log.d("CAMPUS","No such document")
                }
            }


    }
}