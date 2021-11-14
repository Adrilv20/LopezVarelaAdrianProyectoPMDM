package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityFilmDetailsBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FilmDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFilmDetailsBinding

    // any way to have this as "global" for the whole app?
    private val dateFormat : DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO(ask what would be preferrable)
        // como hacerlo con extras[]? pide nullable
        // val film : Film = intent.extras?.get("film") as Film

        val film : Film = intent.getSerializableExtra("film") as Film

        binding.tvDetailsFilmTitle.text = film.title
        binding.tvDirectorDetails.text = film.director
        binding.tvReleaseDateDetails.text = dateFormat.format(film.releaseDate)
    }
}