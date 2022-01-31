package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

// TODO(Changed to data class for the auto implementation of equals and such methods. Search if it may have any demerit)
// Default values to get an empty constructor.
// Changed from val to var in order to modify the film on FilmEditActivity. TODO(search better approach)
data class Film(
        var id: String = "-1",
        var title: String = "",
        @SerializedName("directorFullName")
        var director: String = "",
        @SerializedName("directorPhone")
        var dirPhoneNum: String = "",
        @SerializedName("runtimeMinutes")
        var durationMins: Int = -1,
        var releaseDate: LocalDate = LocalDate.MIN,
        @SerializedName("imageUrl")
        var imageURL: String = "",
        @SerializedName("description")
        var summary: String = ""
) : Serializable {}