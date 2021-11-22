package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityFilmDetailsBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FilmDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFilmDetailsBinding

    private var imageWidth : Int = 0
    private var imageHeight : Int = 0

    // any way to have this as "global" for the whole app? companions
    private val dateFormat : DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageWidth = resources.getDimension(R.dimen.film_poster_list_width).toInt()
        imageHeight = resources.getDimension(R.dimen.film_poster_list_height).toInt()

        val film : Film = intent.getSerializableExtra("film") as Film

        binding.tvDetailsFilmTitle.text = film.title
        binding.tvDirectorDetails.text = film.director
        binding.tvReleaseDate.text = dateFormat.format(film.releaseDate)
        binding.tvDuration.text = film.durationMins.toString() + " mins"
        Picasso.get().load(film.imageURL)
            .resize(imageWidth.toInt(), imageHeight.toInt()).centerCrop()
            .into(binding.ivDetailsFilmPoster)
    }
}