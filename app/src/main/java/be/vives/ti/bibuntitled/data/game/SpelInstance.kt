package be.vives.ti.bibuntitled.data.game
import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.*

/** @author Remi Verhulst */

data class SpelInstance(
    var AantalLeden: Int,
    var AantalVragen: Int,
    var Campus: String,
    var Datum: Timestamp,
    var Score: Int,
    var SpelCode: String,
    var Studiegebied: Int,
    var TeamNaam: String,
    var Timer: Int,
    var VragenFout: MutableList<String>,
    var VragenJuist: MutableList<String>,
    var VragenTip: MutableList<String>
) : Serializable {
    constructor() : this(0,0, "", Timestamp(0,0), 0,"", -1, "", 0, mutableListOf(), mutableListOf(),
        mutableListOf())
}


