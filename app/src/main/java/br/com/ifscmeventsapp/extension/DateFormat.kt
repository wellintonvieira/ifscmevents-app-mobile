package br.com.ifscmeventsapp.extension

fun String.formatDate(date: String): String {
    val dateString = date.split("-")
    val year = dateString[0]
    val mouth = dateString[1]
    val day = dateString[2]
    return "$day/$mouth/$year"
}

fun String.formatHour(hour: String): String {
    val hourString = hour.split(":")
    val hours = hourString[0]
    val minutes = hourString[1]
    return "$hours:$minutes"
}