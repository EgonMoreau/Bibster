package be.vives.ti.bibuntitled.data

import java.io.Serializable
import java.util.*

/** @author Joshua Tack **/
data class NewsItem(val title: String, val description: String, val date: Date) : Serializable {
    constructor() : this("undefined", "undefined", Date(0))
}