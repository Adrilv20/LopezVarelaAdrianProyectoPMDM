package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityFilmEditBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.dateToString

class FilmEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFilmEditBinding
    private var film : Film? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // retrieve film info. Will be null when creating a new film
        film = intent.extras?.get(KEYS.FILM) as Film?

        // set the title of the screen based on whether we're editing o creating
        title = film?.title ?: "New Film"

        // first block gets executed if editing existing film, 2nd block otherwise
        film?.let {
            with(binding){
                // IMPORTANT: must use ?.setText or Kotlin won't find the method overload used.
                etFilmTitle?.setText(it.title)
                etDirectorName?.setText(it.director)
                etDuration?.setText(it.durationMins.toString())
                etReleaseDate?.setText(dateToString(it.releaseDate))
                etTelephoneNomber?.setText(it.dirPhoneNum)
                etImageUrl?.setText(it.imageURL)
                etFilmSummary?.setText("To be implemented") // TODO(unmock the summaries)
            }
        } ?: run {

        }
    }
}