package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// can control the format used for all the dates shown with this formatter
private val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

/* Used to serialize and deserialize LocalDate objects when comunicating with the API.
   Intentionally using snake case naming to prevent mixing it up with the in-app formatter */
private val API_DATE_FORMATTER : DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

val dateInputRegex = Regex("[0-9]{2}/[0-9]{2}/[0-9]{4}")

fun dateToString(date: LocalDate) : String = formatter.format(date)

fun dateFromString(date : String) : LocalDate = LocalDate.from(formatter.parse(date))

fun dateSerializer(date: LocalDate) : String = API_DATE_FORMATTER.format(date)

fun dateDeserializer(date: String) : LocalDate {
    val date = LocalDate.parse(date, API_DATE_FORMATTER)
    return date
}