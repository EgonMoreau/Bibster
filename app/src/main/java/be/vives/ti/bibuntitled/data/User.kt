package be.vives.ti.bibuntitled.data

import java.io.Serializable

data class User(
    var id:String,
    var email: String,
    var voornaam: String,
    var achternaam: String,
    var campus: String,
    var richting: String
):Serializable{
    constructor() : this("", "","","","","")
}
