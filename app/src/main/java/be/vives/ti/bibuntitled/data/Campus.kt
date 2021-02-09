package be.vives.ti.bibuntitled.data

import java.io.Serializable

/** @author Remi Verhulst */

data class Campus(
    val campus: String,
    val bibliotheek: String,
    val adres: String,
    val telefoon: String,
    val email: String,
    val website: String,
    val maps: String,
    val facebook: String,
    val instagram: String,
    val pinterest: String
) : Serializable {
    constructor() : this("", "", "", "", "", "", "", "", "", "")

}