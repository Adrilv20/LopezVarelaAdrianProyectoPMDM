package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// can control the format used for all the dates shown with this formatter
private val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

val dateInputRegex = Regex("[0-9]{2}/[0-9]{2}/[0-9]{4}")

fun dateToString(date: LocalDate) : String = formatter.format(date)

fun dateFromString(date : String) : LocalDate = LocalDate.from(formatter.parse(date))