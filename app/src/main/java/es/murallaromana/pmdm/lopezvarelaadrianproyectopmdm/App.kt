package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm

import android.app.Application
import android.content.Context
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.dao.FilmDAOMockImpl
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import kotlin.collections.ArrayList

class App : Application() {
    companion object GLB_STATE {
        // Static context used by utility classes to access resources
        lateinit var context : Context

        private var nextFilmId : Long = 5L;
        // We keep the inner state hidden to prevent introducing bugs by directly modifying the list from the activities
        // Instead we offer the different operations needed by the rest of the application from this companion object
        // TODO(change to use immutable list)
        private val films : ArrayList<Film> = ArrayList()

        fun addNewFilm(film: Film) {
            this.films.add(film.copy().apply { id = nextFilmId++ })
        }

        fun removeFilm(id: Long) {
            val index = films.indexOfFirst{it.id == id}
            this.films.removeAt(index)
        }

        fun addAll(films : Collection<Film>) {
            films.forEach { this.films.add(it.copy()) }
        }

        /**
         * When asking for all the films, will return a copy of the list to prevent undesired modifications to the inner state of the app
         */
        fun getAllFilms() : List<Film> = this.films.map {it.copy()}

        fun getFilmByIndex(index : Int) : Film = this.films[index]

        fun updateFilm(film : Film) {
            val index = films.indexOfFirst{it.id == film.id}
            films[index] = film.copy()
        }
    }

    override fun onCreate() {
        super.onCreate()
        GLB_STATE.addAll(FilmDAOMockImpl().getAllFilms())
        GLB_STATE.context = this
    }
}