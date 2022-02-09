package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.App.GLB_STATE
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.afterTextChanged
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityFilmEditBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NumberFormatException
import java.time.format.DateTimeParseException

class FilmEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmEditBinding
    private var originalFilm: Film? = null
    private lateinit var newFilm: Film

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
                etDirectorName.setText(it.director)
                etDuration.setText(it.durationMins?.toString())
                etReleaseDate.setText(it.releaseDate?.let { dateToString(it) })
                etTelephoneNomber.setText(it.dirPhoneNum)
                etImageUrl.setText(it.imageURL)
                etFilmSummary.setText(it.summary)
            }
        } ?: run {
            title = getString(R.string.newFilmActivityTitle)
            newFilm = Film()
        }

        // set text watchers to handle changes on the editTexts
        toggleDirtyWatcherOnEditTexts()

        binding.floatingActionButton.setOnClickListener {
            if (filmChanged()) {
                when (newFilm.id) {
                    null -> {
                        callCreateFilm(newFilm)
                    }
                    else -> {
                        callEditFilm(newFilm)
                    }
                }
            } else Toast.makeText(
                this,
                getString(R.string.editActNoChangesToast),
                Toast.LENGTH_SHORT
            ).show()
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
                    newFilm.durationMins = null
                }
            }
            etReleaseDate.afterTextChanged {
                if (dateInputRegex.matches(it)) {
                    try {
                        newFilm.releaseDate = dateFromString(it)
                    } catch (e: DateTimeParseException) {
                        newFilm.releaseDate = null
                    }
                }
            }
            etTelephoneNomber.afterTextChanged { newFilm.dirPhoneNum = it }
            etImageUrl.afterTextChanged { newFilm.imageURL = it }
            etFilmSummary.afterTextChanged { newFilm.summary = it }
        }
    }

    private fun filmChanged(): Boolean = !(originalFilm?.equals(newFilm) ?: (newFilm == Film()))

    private fun callCreateFilm(film: Film) {
        val createCall = RetrofitClient.instance.createFilm(film)
        createCall.enqueue(object : Callback<Film> {
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                        this@FilmEditActivity,
                        "Server side error while creating a new film.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Error creating film", response.errorBody().toString())
                } else {
                    Toast.makeText(
                        this@FilmEditActivity,
                        "Film created",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Film>, t: Throwable) {
                Toast.makeText(
                    this@FilmEditActivity,
                    "Unexpected error while creating a new film.",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("Error creating film", t.toString())
            }
        })

    }

    private fun callEditFilm(newFilm: Film) {
        val editCall = RetrofitClient.instance.editFilm(newFilm)
        editCall.enqueue(object : Callback<Film> {
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                        this@FilmEditActivity,
                        "Server side error while editing the film.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Error editing film", response.errorBody().toString())
                } else {
                    Toast.makeText(
                        this@FilmEditActivity,
                        "Film edited!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(RESULT_OK, Intent().apply {
                        putExtra(
                            KEYS.FILM, response.body() as Film
                        )
                    })
                    finish()
                }

            }

            override fun onFailure(call: Call<Film>, t: Throwable) {
                Toast.makeText(
                    this@FilmEditActivity,
                    "Unexpected error while editing the film.",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("Error editing film", t.toString())
            }
        })
    }

}