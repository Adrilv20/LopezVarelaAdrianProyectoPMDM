package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm

import android.app.Application
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.dao.FilmDAOMockImpl
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import kotlin.collections.ArrayList

class App : Application() {
    companion object GBL_STATE {
        // We keep the inner state hidden to prevent introducing bugs by directly modifying the list from other activities
        // Instead we offer the different operations needed by the rest of the application from this companion object
        // TODO(change to use immutable list)
        private val films : ArrayList<Film> = ArrayList()

        fun addFilm(film: Film) {
            films.add(film)
        }

        fun removeFilm(film: Film) {
            films.remove(film)
        }

        fun addAll(films : Collection<Film>) {
            this.films.addAll(films)
        }

        /**
         * When asking for all the films, will return a copy of the list to prevent undesired modifications to the inner state of the app
         * actual modifications to the state of the app (film list) should be performed through the add/remove methods
         */
        fun getAllFilms() = films.toList()

        fun getFilmByIndex(index : Int) = this.films[index]
    }

    override fun onCreate() {
        GBL_STATE.addAll(FilmDAOMockImpl().getAllFilms())
        super.onCreate()
    }
}