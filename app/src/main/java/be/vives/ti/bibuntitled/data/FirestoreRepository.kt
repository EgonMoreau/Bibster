package be.vives.ti.bibuntitled.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import be.vives.ti.bibuntitled.MainActivity
import be.vives.ti.bibuntitled.data.game.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FirestoreRepository {

    var firestoreDB = FirebaseFirestore.getInstance()
    private lateinit var database: DatabaseReference

    interface GetGameInstancesCallback {
        fun onCallback(spelInstances: List<SpelInstance>)
    }

    fun getGameInstances(callback: GetGameInstancesCallback) {
        var spelInstances: List<SpelInstance> = emptyList()
        firestoreDB.collection("SpelStatistiek").get()
            .addOnSuccessListener { documents ->
                if (documents != null && !documents.isEmpty) {
                    spelInstances = documents.toObjects(SpelInstance::class.java)
                }
            }
            .addOnCompleteListener {
                callback.onCallback(spelInstances)
            }
    }

    companion object {
        val instance: FirestoreRepository = FirestoreRepository()
    }

    private fun getInfo(): CollectionReference {
        return firestoreDB.collection("Info")
    }
    /** @author Remi Verhulst */
    fun getCampus(): CollectionReference {
        return firestoreDB.collection("Campus")
    }

    private fun getNews(): CollectionReference {
        return firestoreDB.collection("News")
    }

    interface GetInfoItemsCallback {
        fun onCallback(infoItem: InfoItem)
    }

    interface GetNewsItemsCallback {
        fun onCallback(newsItems: List<NewsItem>)
    }

    fun getInfoItems(callback: GetInfoItemsCallback) {
        var infoItem: InfoItem?
        infoItem = null
        getInfo().addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }
                for (document in snapshot!!.documents) {
                    val result = document.toObject(InfoItem::class.java)
                    if (result != null) {
                        infoItem = result
                    }
                }
                infoItem?.let { callback.onCallback(it) }
            }

    }

    /** @author Joshua Tack
     *  Haal data op uit firebase en sorteer ze volgens nieuwste eerst
     */

    fun getNewsItems(callback: GetNewsItemsCallback) {
        val newsItems = mutableListOf<NewsItem>()
        getNews().orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }
                for (document in snapshot!!.documents) {
                    val result = document.toObject(NewsItem::class.java)
                    if (result != null) {
                        newsItems.add(result)
                    }
                }
                callback.onCallback(newsItems)
            }
    }

    interface GetSpelCallback {
        fun onCallback(spel: Spel?)
    }

    fun getSpel(spelCode: String, callback: GetSpelCallback) {
        var spel: Spel? = null
        getSpellen().whereEqualTo("Code", spelCode).get()
            .addOnSuccessListener { documents ->
                if (documents != null && !documents.isEmpty) {
                    spel = documents.toObjects(Spel::class.java)[0]
                }
            }
            .addOnFailureListener { exception ->
                Log.e("EXCEPTIONNIG", "get failed with ", exception)
            }
            .addOnCompleteListener {
                callback.onCallback(spel)
            }
    }

    interface GetAnswersCallback {
        fun onCallback(answers: List<Answer>)
    }

    fun getAnswers(question: Question, callback: GetAnswersCallback) {
        var answers: List<Answer> = emptyList()
        getAntwoorden().whereEqualTo("IdBijhorendeVraag", question.documentId).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    answers = documents.toObjects(Answer::class.java)
                    Log.d("QUESTION", answers.toString())
                } else {
                    Log.d("QUESTION", "No such item")
                }
            }
            .addOnFailureListener{exception ->
                Log.d("Question", "get failed with", exception)
            }
            .addOnCompleteListener {
                callback.onCallback(answers)
            }
    }

    interface GetNextQuestionCallback {
        fun onCallback(question: Question?)
    }

    fun getNextQuestion(spel: Spel, callback: GetNextQuestionCallback) {
        var question: Question? = null

        if(spel.Vragen.size > spel.vraagIndex) { // IndexOutOfBounds check
            getVragen().document(spel.Vragen[spel.vraagIndex]).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        question = document.toObject(Question::class.java)
                        question!!.documentId = spel.Vragen[spel.vraagIndex]
                        Log.d("SPEL", question.toString())
                        spel.vraagIndex++
                    } else {
                        Log.d("SPEL", "No such item")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("SPEL", "get failed with", exception)
                }
                .addOnCompleteListener {
                    callback.onCallback(question)
                }
        } else {
            // Er is geen volgende vraag, return null
            callback.onCallback(null)
        }
    }

    private fun getUsers(): CollectionReference {
        return firestoreDB.collection("Gebruiker")
    }

    fun getUser(gebruikersnaam: String, activity: MainActivity): User {
        var gebruiker = User()
        getUsers().document(gebruikersnaam).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject(User::class.java)
                    if (user != null) {
                        gebruiker = user
                        gebruiker.id = gebruikersnaam
                        activity.setGebruikerActivity(gebruiker)
                    }
                } else {
                    Log.e("GEBRUIKER", "No such document")
                }
            }.addOnFailureListener { exception ->
                Log.e("EXCEPTIONGETUSER", "get failed with ", exception)
            }
        return gebruiker

    }

    fun addUser(user: User): User {
        val gebruiker = hashMapOf(
            "email" to user.email, "voornaam" to user.voornaam, "achternaam" to user.achternaam,
            "campus" to user.campus, "richting" to user.richting
        )
        firestoreDB.collection("Gebruiker").document(user.id).set(gebruiker)
        return user
    }

    fun getVragen(): CollectionReference {
        return firestoreDB.collection("Vragen")
    }

    private fun getSpellen(): CollectionReference {
        return firestoreDB.collection("Spellen")
    }

    fun getAntwoorden(): CollectionReference {
        return firestoreDB.collection("Antwoorden")
    }

    fun createChat(groep: String): String {
        database = Firebase.database.reference
        val key = database.child(groep).push().key
        if (key == null) {
            Log.e("CREATECHAT", "Couldn't key push key")
            return ""
        }
        return key

    }

    interface GetChatBerichtenCallback {
        fun onCallback(chatBerichten: List<ChatBericht>)
    }

    fun getBerichten(groep: String, callback: GetChatBerichtenCallback) {
        val toReturn = mutableListOf<ChatBericht>()
        toReturn.clear()
        FirebaseDatabase.getInstance().reference.child(groep).addChildEventListener(
            object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    callback.onCallback(toReturn)
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    p0.children.forEach { _ ->
                        val chatBericht = p0.getValue(ChatBericht::class.java) as ChatBericht
                        if (!(toReturn.contains(chatBericht))) {
                            toReturn.add(chatBericht)
                        }
                    }
                    callback.onCallback(toReturn)
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    p0.children.forEach { _ ->
                        val chatBericht = p0.getValue(ChatBericht::class.java) as ChatBericht
                        if (!(toReturn.contains(chatBericht))) {
                            toReturn.add(chatBericht)
                        }
                    }
                    callback.onCallback(toReturn)
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    p0.children.forEach { _ ->
                        val chatBericht = p0.getValue(ChatBericht::class.java) as ChatBericht
                        if (!(toReturn.contains(chatBericht))) {
                            toReturn.add(chatBericht)
                        }
                    }
                    callback.onCallback(toReturn)
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("Not yet implemented")
                }

            }
        )

    }
    /** @author Remi Verhulst */
    fun getTeam(): CollectionReference {
        return firestoreDB.collection("Team")
    }
    /** @author Remi Verhulst */
    fun addSpelInstance(spelInstances: SpelInstance) {
        val si = hashMapOf(
            "AantalLeden" to spelInstances.AantalLeden,
            "AantalVragen" to spelInstances.AantalVragen,
            "Campus" to spelInstances.Campus,
            "Datum" to spelInstances.Datum,
            "Score" to spelInstances.Score,
            "SpelCode" to spelInstances.SpelCode,
            "Studiegebied" to spelInstances.Studiegebied,
            "TeamNaam" to spelInstances.TeamNaam,
            "Time" to spelInstances.Timer,
            "VragenFout" to spelInstances.VragenFout,
            "VragenJuist" to spelInstances.VragenJuist,
            "VragenTips" to spelInstances.VragenTip
        )
        firestoreDB.collection("SpelStatistiek").add(si)
    }
    /** @author Remi Verhulst */
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTeam(team: Team): Team {
        val t = hashMapOf(
            "teamnaam" to team.teamnaam, "aantal" to team.aantal, "datum" to team.datum,
            "campus" to team.campus, "richting" to team.richting
        )
        firestoreDB.collection("Team").add(t)
        return team
    }

    interface StuurBerichtCallback {
        fun onCallback(success: Boolean)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stuurBericht(bericht: ChatBericht, groep: String, callback: StuurBerichtCallback) {
        database = Firebase.database.reference
        val dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val localDateTime = LocalDateTime.now().plusHours(2)
        database.child(groep).child(dateFormat.format(localDateTime).toString()).setValue(bericht)
            .addOnCompleteListener { document ->
                callback.onCallback(document.isSuccessful)
            }
    }
}