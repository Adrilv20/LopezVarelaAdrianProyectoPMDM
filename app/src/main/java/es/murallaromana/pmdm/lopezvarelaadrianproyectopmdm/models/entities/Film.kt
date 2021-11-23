package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities

import java.io.Serializable
import java.time.LocalDate

class Film(val title: String, val director: String, val dirPhoneNum : String, val durationMins: Int, val releaseDate: LocalDate, val imageURL: String = "") : Serializable {}