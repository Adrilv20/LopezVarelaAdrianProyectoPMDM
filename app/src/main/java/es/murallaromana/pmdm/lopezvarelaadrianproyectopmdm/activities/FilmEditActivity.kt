package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.afterTextChanged
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityFilmEditBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.dateToString

class FilmEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmEditBinding
    private var film: Film? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // retrieve film info. Will be null when creating a new film
        film = intent.extras?.get(KEYS.FILM) as Film?

        // set text watchers to know if there's new data
        toggleDirtyWatcherOnEditTexts()

        // first block gets executed if editing existing film, 2nd block otherwise
        film?.let {
            title = it.title
            with(binding) {
                // IMPORTANT: must use ?.setText or Kotlin/Android Studio? won't find the method overload used.
                etFilmTitle.setText(it.title)
                etFilmTitle?.setText(it.title)
                etDirectorName?.setText(it.director)
                etDuration?.setText(it.durationMins.toString())
                etReleaseDate?.setText(dateToString(it.releaseDate))
                etTelephoneNomber?.setText(it.dirPhoneNum)
                etImageUrl?.setText(it.imageURL)
                etFilmSummary?.setText("To be implemented") // TODO(unmock the summaries)
            }
        } ?: run {
            title = "New Film Info"
        }
    }

    override fun onBackPressed() {
        if (false) { // TODO(check if film changed)
            AlertDialog.Builder(this)
                .setTitle("Exiting Edit View")
                .setMessage("Changes won't be saved. Are you sure you want to leave?")
                .setPositiveButton("Leave", { _, _ ->
                    super.onBackPressed()
                })
                .setNegativeButton("Stay", null)
                .create().show()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun toggleDirtyWatcherOnEditTexts() {
        with(binding) {
            // TODO(FIX THIS)
            etFilmTitle.afterTextChanged {  }
            etDirectorName.afterTextChanged {  }
            etDuration.afterTextChanged {  }
            etReleaseDate.afterTextChanged {  }
            etTelephoneNomber.afterTextChanged {  }
            etImageUrl.afterTextChanged {  }
            etFilmSummary.afterTextChanged {  }
        }
    }
}