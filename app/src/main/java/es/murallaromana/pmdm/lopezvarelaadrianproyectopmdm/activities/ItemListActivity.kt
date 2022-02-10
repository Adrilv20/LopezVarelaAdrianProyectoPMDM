package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.adapters.FilmListAdapter
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityItemListBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.RetrofitClient
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityItemListBinding
    private lateinit var view : RecyclerView
    private val films : MutableList<Film> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set the screen title
        title = resources.getString(R.string.filmListViewTitle)

        // set up the recyclerview
        view = binding.rvFilmList
        with(view) {
            // optimization for the RecyclerView rendering
            setHasFixedSize(true)
            // set the layout manager and the adapter for the RecyclerView
            layoutManager = LinearLayoutManager(this@ItemListActivity)
            adapter = FilmListAdapter(films, this@ItemListActivity)
        }

        val fab : FloatingActionButton = binding.fabAddItem
        fab.setOnClickListener {
            val newFilmIntent = Intent(this, FilmEditActivity::class.java)
            startActivity(newFilmIntent)
        }

    }

    override fun onResume() {
        super.onResume()

        // fetch movies from the API
        val moviesCall = RetrofitClient.instance.getFilms()
        moviesCall.enqueue(object : Callback<List<Film>> {
            override fun onResponse(call: Call<List<Film>>, response: Response<List<Film>>) =
                if(response.isSuccessful) {
                    films.clear()
                    films.addAll(response.body() as List<Film>)
                    view.adapter!!.notifyDataSetChanged()
                } else {
                    handleErrorFetchingFilms("Error while fetching the films: " + response.errorBody()?.string())
                    // since there's no direct way to tell if the error was due to expired token, we'll have to assume so
                    // token got already cleared by the method above. going back to login history now
                    goToLogin()
                }

            override fun onFailure(call: Call<List<Film>>, t: Throwable) {
                handleErrorFetchingFilms("Unexpected error while fetching movies:$t")
                throw t
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_film_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        return when(item.itemId) {
            R.id.action_log_out -> {
                SessionManager.clearToken()
                onBackPressed()
                true
            }
            else -> true
        }
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
    }

    private fun handleErrorFetchingFilms(error : String){
        Toast.makeText(this@ItemListActivity, error, Toast.LENGTH_LONG).show()
        // Clear token so the app doesn't get stuck on this activity without a valid token.
        // worst case scenario, will get stuck, and by clearing it next time will show the login screen.
        SessionManager.clearToken()
    }

    private fun goToLogin() = startActivity(Intent(this@ItemListActivity,LoginActivity::class.java))

}