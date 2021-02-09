package be.vives.ti.bibuntitled.data


data class InfoItem(
    var Mededeling: String? = null,
    var mondayHours: String? = null,
    var tuesdayHours: String? = null,
    var wednesdayHours: String? = null,
    var thursdayHours: String? = null,
    var fridayHours: String? = null

) {
    constructor() : this("", "", "", "", "", "")
}