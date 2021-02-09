package be.vives.ti.bibuntitled.data.game
import com.google.firebase.firestore.Exclude

data class Spel(
    var Campus: String,
    var Code: String,
    var Commentaar: String,
    var Naam: String,
    var Omschrijving: String,
    var Studiegebied: Int,
    var Vragen: List<String>
) {
    @get:Exclude var vraagIndex: Int = 0
    @get:Exclude var isFinished = false

    constructor() : this("", "","","","",0, emptyList())
}
