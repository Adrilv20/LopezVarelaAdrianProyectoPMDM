package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

// Default values to get an empty constructor.
// Changed from val to var in order to modify the film on FilmEditActivity.
data class Film(
        var id: String? = null,
        var title: String = "",
        @SerializedName("directorFullname")
        var director: String = "",
        @SerializedName("directorPhone")
        var dirPhoneNum: String = "",
        @SerializedName("runtimeMinutes")
        var durationMins: Int? = null,
        @Expose(serialize = true, deserialize = true)
        var releaseDate: LocalDate? = null,
        @SerializedName("imageUrl")
        var imageURL: String? = null,
        @SerializedName("description")
        var summary: String = ""
) : Serializable {}