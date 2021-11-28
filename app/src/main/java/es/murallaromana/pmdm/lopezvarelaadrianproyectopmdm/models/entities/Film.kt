package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities

import java.io.Serializable
import java.time.LocalDate

// TODO(Changed to data class for the auto implementation of equals and such methods. Search if it may have any demerit)
// Default values to get an empty constructor.
// Changed from val to var in order to modify the film on FilmEditActivity. TODO(search better approach)
data class Film(
        var id: Long = -1,
        var title: String = "",
        var director: String = "",
        var dirPhoneNum: String = "",
        var durationMins: Int = -1,
        var releaseDate: LocalDate = LocalDate.MIN,
        var imageURL: String = ""
) : Serializable {}