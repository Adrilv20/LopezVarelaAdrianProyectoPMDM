package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.squareup.picasso.Picasso
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityFilmDetailsBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FilmDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFilmDetailsBinding

    // currently only used on onCreate method, but leaving them outside of it in case
    // they're needed on other methods.
    private var imageWidth : Int = 0
    private var imageHeight : Int = 0

    // TODO(move it to a companion so it's global for the whole app)
    private val dateFormat : DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set the title for the view
        setTitle(getString(R.string.filmDetailsViewTitle))

        // get the image dimensions from the correspoding resources file for future use.
        imageWidth = resources.getDimension(R.dimen.film_poster_details_width).toInt()
        imageHeight = resources.getDimension(R.dimen.film_poster_details_height).toInt()

        // retrieve the corresponding film object from the intent
        val film : Film = intent.getSerializableExtra("film") as Film

        // set the film data on the corresponding fields
        binding.tvDetailsFilmTitle.text = film.title
        binding.tvDirectorDetails.text = "Directed by " + film.director
        binding.tvReleaseDate.text = dateFormat.format(film.releaseDate)
        // TODO(change duration format to "Xh Ymin")
        binding.tvDuration.text = film.durationMins.toString() + " mins"
        binding.tvDescription.apply {
            text = resources.getString(R.string.lorem_ipsum)
            movementMethod = ScrollingMovementMethod()
        }
        Picasso.get().load(film.imageURL)
            .resize(imageWidth, imageHeight)
            .into(binding.ivDetailsFilmPoster)
    }
}