package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

// Default values to get an empty constructor.
// Changed from val to var in order to modify the film on FilmEditActivity.
data class Film(
        var id: String = "-1",
        var title: String = "",
        @SerializedName("directorFullName")
        var director: String = "",
        @SerializedName("directorPhone")
        var dirPhoneNum: String = "",
        @SerializedName("runtimeMinutes")
        var durationMins: Int = -1,
        // TODO ask for change on the back to include full date?
        var releaseDate: LocalDate = LocalDate.MIN,
        @SerializedName("imageUrl")
        var imageURL: String = "",
        @SerializedName("description")
        var summary: String = ""
) : Serializable {}