package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityFilmDetailsBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS
import java.lang.IllegalArgumentException
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FilmDetailsActivity : AppCompatActivity() {
    companion object {
        internal val FILM_EDIT_RESULT: Int = 1
    }

    private lateinit var binding: ActivityFilmDetailsBinding
    private lateinit var film: Film

    // currently only used on onCreate method, but leaving them outside of it in case they're needed on other methods.
    private var imageWidth: Int = 0
    private var imageHeight: Int = 0

    // Custom formatter for this activity
    private val dateFormat: DateTimeFormatter =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set the title for the view
        setTitle(getString(R.string.filmDetailsViewTitle))

        // retrieve the film passed from the parent activity
        film = intent.getSerializableExtra(KEYS.FILM) as Film

        // get the image dimensions from the correspoding resources file for future use.
        imageWidth = resources.getDimension(R.dimen.film_poster_details_width).toInt()
        imageHeight = resources.getDimension(R.dimen.film_poster_details_height).toInt()
    }

    override fun onResume() {
        super.onResume()

        // set the film data on the corresponding fields
        with(binding) {
            tvDetailsFilmTitle.text = film.title
            tvDirectorDetails.text = getString(R.string.directed_by_prefix) + " " + film.director
            tvReleaseDate.text.apply {
                film.releaseDate?.let{ dateFormat.format(it)} // TODO test this works as intended once dates are implemented
            }
            // TODO(change duration format to "Xh Ymin")
            tvDuration.text = film.durationMins.toString() + " mins"
            tvDescription.text = film?.summary
            try {
                Picasso.get().load(film.imageURL)
                    .resize(imageWidth, imageHeight)
                    .into(ivDetailsFilmPoster)
            } catch (ex: IllegalArgumentException) {
                ivDetailsFilmPoster.setImageResource(R.drawable.ic_image_not_found)
            }
            tvTelephone.apply {
                // Using an instance of SpannableString to make the phone number be underlined
                with(SpannableString(film.dirPhoneNum)) {
                    setSpan(UnderlineSpan(), 0, this.length, 0)
                    text = this
                }
                setOnClickListener {
                    val callIntent = Intent(Intent.ACTION_DIAL).apply {
                        setData(Uri.parse("tel:" + film.dirPhoneNum))
                    }
                    startActivity(callIntent)
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // inflate the corresponding menu for this activity
        menuInflater.inflate(R.menu.menu_item_details, menu)
        // this method expects true to be returned in order to show the menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // move to edit activity, passing the film data through the intent extra
            R.id.action_save_or_update -> {
                val intent = Intent(this, FilmEditActivity::class.java)
                intent.putExtra(KEYS.FILM, film)
                // TODO(If possible, change to the more recent way to pass results: https://developer.android.com/training/basics/intents/result )
                // while deprecated, using this right now because of time constrains
                startActivityForResult(intent, FILM_EDIT_RESULT)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FILM_EDIT_RESULT ->
                if (resultCode == RESULT_OK) {
                    // recover the edited film. Will be displaed by onResume
                    film = data?.getSerializableExtra(KEYS.FILM) as Film
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}