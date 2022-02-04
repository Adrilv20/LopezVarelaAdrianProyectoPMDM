package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.App.GLB_STATE
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.afterTextChanged
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityFilmEditBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.dateFromString
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.dateToString
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.dateInputRegex
import java.lang.NumberFormatException

class FilmEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmEditBinding
    private var originalFilm: Film? = null
    private lateinit var newFilm: Film
    private var creating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // retrieve film info. Will be null when creating a new film
        originalFilm = intent.extras?.get(KEYS.FILM) as Film?

        // first block gets executed if editing existing film, 2nd block otherwise
        originalFilm?.let {
            title = it.title
            newFilm = it.copy()
            with(binding) {
                etFilmTitle.setText(it.title)
                etFilmTitle.setText(it.title)
                etDirectorName.setText(it.director)
                etDuration.setText(it.durationMins.toString())
                etReleaseDate.setText(dateToString(it.releaseDate))
                etTelephoneNomber.setText(it.dirPhoneNum)
                etImageUrl.setText(it.imageURL)
                etFilmSummary.setText(it.summary)
            }
        } ?: run {
            creating = true
            title = getString(R.string.newFilmActivityTitle)
            newFilm = Film()
        }

        // set text watchers to handle changes on the editTexts
        toggleDirtyWatcherOnEditTexts()

        binding.floatingActionButton.setOnClickListener {
            if (filmChanged()) {
                when (newFilm.id) {
                    null -> GLB_STATE.addNewFilm(newFilm)
                    else -> {
                        GLB_STATE.updateFilm(newFilm)
                        setResult(RESULT_OK, Intent().apply { putExtra(KEYS.FILM, newFilm) })
                    }
                }
            } else Toast.makeText(this, getString(R.string.editActNoChangesToast), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onBackPressed() {
        if (filmChanged()) {
            AlertDialog.Builder(this)
                    .setTitle(getString(R.string.editActLeaveTitle))
                    .setMessage(getString(R.string.editActLeaveMessage))
                    .setPositiveButton(getString(R.string.editActLeaveButton), { _, _ ->
                        super.onBackPressed()
                    })
                    .setNegativeButton(getString(R.string.editActStayButton), null)
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
        return true // leaving super.onOptionsItemSelected(item) will call to onBackPressed for button android.R.id.home
    }

    private fun toggleDirtyWatcherOnEditTexts() {
        with(binding) {
            // this isn't particularly efficient and probably not the best alternative, but keeping it this way
            // to have an example about extensions and TextWatchers
            etFilmTitle.afterTextChanged { newFilm.title = it }
            etDirectorName.afterTextChanged { newFilm.director = it }
            etDuration.afterTextChanged {
                try {
                    newFilm.durationMins = it.toInt()
                } catch (e: NumberFormatException) {
                    // TODO(inform the user that this is not a valid value)
                }
            }
            etReleaseDate.afterTextChanged { if (dateInputRegex.matches(it)) newFilm.releaseDate = dateFromString(it) }
            etTelephoneNomber.afterTextChanged { newFilm.dirPhoneNum = it }
            etImageUrl.afterTextChanged { newFilm.imageURL = it }
            etFilmSummary.afterTextChanged { newFilm.summary = it }
        }
    }

    private fun filmChanged(): Boolean = !(originalFilm?.equals(newFilm) ?: (newFilm == Film()))
}