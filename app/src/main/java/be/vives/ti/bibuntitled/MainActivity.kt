package be.vives.ti.bibuntitled
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import be.vives.ti.bibuntitled.data.FirestoreRepository
import be.vives.ti.bibuntitled.data.User
import be.vives.ti.bibuntitled.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

class MainActivity : AppCompatActivity() {
    private lateinit var gebruikerActivity : User
    private lateinit var auth: FirebaseAuth
    private lateinit var firestoreRepository: FirestoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gebruikerActivity = User()
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        firestoreRepository = FirestoreRepository.instance
        auth = FirebaseAuth(Firebase.app)
    }

    fun getGebruikerActivity():User{
        return gebruikerActivity
    }

    fun setGebruikerActivity(gebruiker: User?){
        gebruikerActivity = gebruiker as User
    }

    interface GetLoginCallback {
        fun onCallback(successful: Boolean)
    }

    fun login(email: String, wachtwoord: String, callback: GetLoginCallback) {
        var ok: Boolean
        if(email.isNotEmpty() && wachtwoord.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, wachtwoord).addOnCompleteListener(this) {
                ok = if (it.isSuccessful) {
                    val user = auth.currentUser
                    val sharedPref = getPreferences(Context.MODE_PRIVATE)
                    with (sharedPref.edit()) {
                        putString("email", email)
                        putString("password", wachtwoord)
                        commit()
                    }

                    updateUI(user)
                } else {
                    updateUI(null)
                }
                callback.onCallback(ok)
            }
        }
    }

    fun createAccount(
        gebruiker: User,
        wachtwoord: String,
        view: View?
    ) {
        auth.createUserWithEmailAndPassword(gebruiker.email, wachtwoord)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e("MAKENSUCCESS", "createUserWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        gebruiker.id = user.uid
                    }
                    firestoreRepository.addUser(gebruiker)
                    updateUIRegister(user, view, 0)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("MAKENFAIL", "createUserWithEmail:failure", task.exception)
                    updateUIRegister(null, view, 1)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?): Boolean {
        if(user != null) {
            findNavController(R.id.myNavHostFragment).navigate(R.id.action_loginFragment_to_dashboardFragment)
            firestoreRepository.getUser(user.uid, this)
            return true
        }
        return false
    }

    private fun updateUIRegister(
        user: FirebaseUser?,
        view: View?,
        i: Int
    ){
        if(i==0) {
            view?.findNavController()?.navigate(R.id.action_registrerenFragment_to_dashboardFragment)
            firestoreRepository.getUser(user!!.uid, this)
        }


    }
}