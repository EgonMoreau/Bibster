package be.vives.ti.bibuntitled.data.game

import com.google.firebase.firestore.Exclude

class Question {
    var Campus: String = "8500"
    var Feedback: String? = null
    var Omschrijving: String = "Lege vraag."
    var Studiegebied: Int = 0
    var Tip: String? = null
    var TypeVanAntwoorden: Int = 0
    @get:Exclude lateinit var documentId: String
    @get:Exclude var pointsAwarded: Int = 0
}
