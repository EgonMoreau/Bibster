package be.vives.ti.bibuntitled.data.game

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp

/** @author Remi Verhulst */

data class Team(
    var teamnaam: String,
    var aantal: Int,
    var datum: Timestamp,
    var campus: String,
    var richting: String
) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this("", 0, Timestamp(0, 0), "", "")
}