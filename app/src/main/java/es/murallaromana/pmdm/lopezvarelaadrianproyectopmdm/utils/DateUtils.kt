package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// can control the format used for all the dates shown with this formatter
private val formatter : DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

fun dateToString(date: LocalDate) : String = formatter.format(date)

fun dateFromString(date : String) : LocalDate = LocalDate.from(formatter.parse(date))