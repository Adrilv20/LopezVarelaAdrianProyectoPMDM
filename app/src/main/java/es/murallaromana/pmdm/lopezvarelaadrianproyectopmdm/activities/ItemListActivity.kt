package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.app.AlertDialog
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
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.actionHandlers.ClickHandler
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.adapters.FilmListAdapter
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityItemListBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.RetrofitClient
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemListActivity : AppCompatActivity(), ClickHandler {
    private lateinit var binding : ActivityItemListBinding
    private lateinit var recyclerView : RecyclerView
    private val films : MutableList<Film> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set the screen title
        title = resources.getString(R.string.filmListViewTitle)

        // set up the recyclerview
        recyclerView = binding.rvFilmList
        with(recyclerView) {
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
                    recyclerView.adapter!!.notifyDataSetChanged()
                } else if (response.code() == 401) {
                    handleErrorFetchingFilms(getString(R.string.expired_token_error))
                    goToLogin()
                }
                else {
                    handleErrorFetchingFilms(getString(R.string.error_film_list_fetching) + response.errorBody()?.string())
                    goToLogin()
                }

            override fun onFailure(call: Call<List<Film>>, t: Throwable) {
                handleErrorFetchingFilms(getString(R.string.error_film_list_fetching))
                Log.d("Error fetching", t.toString())
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

    override fun onItemClickHandler(position: Int) {
        val filmId = films[position].id!!
        val getByIdCall = RetrofitClient.instance.getFilmById(filmId)
        getByIdCall.enqueue(object : Callback<Film> {
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                if (response.isSuccessful) {
                    val intent =
                        Intent(this@ItemListActivity, FilmDetailsActivity::class.java).apply {
                            putExtra(
                                KEYS.FILM,
                                response.body() as Film
                            )
                            // the cast may not be needed
                        }
                    this@ItemListActivity.startActivity(intent)
                } else if (response.code() == 401) {

                } else {
                    Toast.makeText(
                        this@ItemListActivity,
                        getString(R.string.error_fetching_film),
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Error GET movie/{id}: ", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Film>, t: Throwable) {
                Toast.makeText(
                    this@ItemListActivity,
                    getString(R.string.error_fetching_film),
                    Toast.LENGTH_LONG
                ).show()
                Log.d("Error fetching", t.toString())
            }
        })
    }

    override fun onItemLongClickHandler(position: Int) : Boolean {
        var result: Boolean = false
        AlertDialog.Builder(this)
            .setTitle("Deleting film")
            .setMessage("About to delete film " + films[position].title)
            .setPositiveButton("Delete") { _, _ ->
                callDeleteFilm(films[position].id!!)
                result = true
            }
            .setNegativeButton("Keep", null)
            .create().show()
        return result
    }


    private fun handleErrorFetchingFilms(error : String){
        Toast.makeText(this@ItemListActivity, error, Toast.LENGTH_LONG).show()
        // Clear token so the app doesn't get stuck on this activity without a valid token.
        // worst case scenario, will get stuck, and by clearing it next time will show the login screen.
        SessionManager.clearToken()
    }

    private fun goToLogin() {
        startActivity(Intent(this@ItemListActivity,LoginActivity::class.java))
        finish()
    }

    private fun callDeleteFilm(filmId: String) {
        val deleteCall = RetrofitClient.instance.deleteFilm(filmId)
        deleteCall.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    with(this) {
                        val pos = films.indexOfFirst { it.id == filmId }
                        if (pos != -1) {
                            films.removeAt(pos)
                            recyclerView.adapter!!.notifyItemRemoved(pos)
                        }
                    }
                } else {
                    Toast.makeText(
                        this@ItemListActivity,
                        getString(R.string.server_error_film_delete),
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Error deleting film", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(
                    this@ItemListActivity,
                    getString(R.string.unkown_error_film_delete),
                    Toast.LENGTH_LONG
                ).show()
                Log.d("Error deleting film", t.toString())
            }
        })
    }
}