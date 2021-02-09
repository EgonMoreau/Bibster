package be.vives.ti.bibuntitled.data

import com.google.firebase.database.PropertyName
import java.util.*

data class ChatBericht(
    @PropertyName("message") var message: String,
    @PropertyName("sender") var sender: String,
    var date: Date
) {
    constructor() : this("", "", Date(0))
}