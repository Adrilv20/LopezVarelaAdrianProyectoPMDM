package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private lateinit var films : List<Film>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set the screen title
        title = resources.getString(R.string.filmListViewTitle)

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
        moviesCall.enqueue(object : Callback<List<Film>>{
            override fun onResponse(call: Call<List<Film>>, response: Response<List<Film>>) {
                if(response.isSuccessful) {
                    films = response.body() as List<Film>
                    films.forEach { Log.d("Film", it.toString()) }
                    // set the layout manager and the adapter for the RecyclerView
                    view = binding.rvFilmList
                    view.layoutManager = LinearLayoutManager(this@ItemListActivity)
                    view.adapter = FilmListAdapter(films, this@ItemListActivity)
                    // optimization for the RecyclerView rendering
                    view.setHasFixedSize(true)
                } else {
                    handleErrorFetchingFilms("Error while fetching the films: " + response.errorBody()?.string())
                    // since there's no direct way to tell if the error was due to expired token, we'll have to asume so
                    // token got already cleared by the method above. going back to login history now
                    goToLogin()
                }
            }

            override fun onFailure(call: Call<List<Film>>, t: Throwable) {
                handleErrorFetchingFilms("Unexpected error while fetching movies:" + t.toString())
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
                // SessionManager.clearToken()
                onBackPressed()
                true
            }
            else -> true
        }
    }

    override fun onBackPressed() {
        /*
        val closeAppIntent = Intent(Intent.ACTION_MAIN)
        closeAppIntent.addCategory(Intent.CATEGORY_HOME)
        closeAppIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(closeAppIntent)
        */
        finishAndRemoveTask()
        // TODO figure out how to properly close the application.
        // Most solutions either leave the application on the background or return to login.
    }

    private fun handleErrorFetchingFilms(error : String){
        Toast.makeText(this@ItemListActivity, error, Toast.LENGTH_LONG)
        // Clear token so the app doesn't get stuck on this activity without a valid token.
        // worst case scenario, will get stuck, and by clearing it next time will show the login screen.
        SessionManager.clearToken()
    }

    private fun goToLogin() = startActivity(Intent(this@ItemListActivity,LoginActivity::class.java))

}