package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.UnderlineSpan
import com.squareup.picasso.Picasso
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityFilmDetailsBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FilmDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmDetailsBinding

    // currently only used on onCreate method, but leaving them outside of it in case they're needed on other methods.
    private var imageWidth: Int = 0
    private var imageHeight: Int = 0

    // TODO(move it to a companion so it's global for the whole app)
    private val dateFormat: DateTimeFormatter =
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

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
        val film: Film = intent.getSerializableExtra(KEYS.FILM) as Film

        // set the film data on the corresponding fields
        with(binding) {
            tvDetailsFilmTitle.text = film.title
            tvDirectorDetails.text = getString(R.string.directed_by_prefix) + film.director
            tvReleaseDate.text = dateFormat.format(film.releaseDate)
            // TODO(change duration format to "Xh Ymin")
            tvDuration.text = film.durationMins.toString() + " mins"
            tvDescription.apply {
                text = resources.getString(R.string.lorem_ipsum)
                movementMethod = ScrollingMovementMethod()
            }
            Picasso.get().load(film.imageURL)
                .resize(imageWidth, imageHeight)
                .into(ivDetailsFilmPoster)
            tvTelephone.apply{
                // Using an instance of SpannableString to make the phone number be underlined
                // this will indicate the user that it can be pressed for an action
                with(SpannableString(film.dirPhoneNum)) {
                    setSpan(UnderlineSpan(),0, this.length,0)
                    text = this
                }
                setOnClickListener {
                    val callIntent = Intent(Intent.ACTION_DIAL)
                    callIntent.setData(Uri.parse("tel:" + film.dirPhoneNum))
                    startActivity(callIntent)
                }
            }
        }
    }
}