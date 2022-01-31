package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.adapters.FilmListAdapter
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityItemListBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.RetrofitClient
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
                    // set the layout manager and the adapter for the RecyclerView
                    view = binding.rvFilmList
                    view.layoutManager = LinearLayoutManager(this@ItemListActivity)
                    view.adapter = FilmListAdapter(films, this@ItemListActivity)
                    // optimization for the RecyclerView rendering
                    view.setHasFixedSize(true)
                } else {
                    Toast.makeText(this@ItemListActivity, "Error while fetching the films: " + response.errorBody()?.string(), Toast.LENGTH_LONG)

                }
            }

            override fun onFailure(call: Call<List<Film>>, t: Throwable) {
                // IMPLEMENT FALLBACK TO STORED FILMS?
                Toast.makeText(
                    this@ItemListActivity,
                    "Unexpected error while fetching movies:" + t.toString(),
                    Toast.LENGTH_LONG
                ).show()
                return
                TODO("Not yet implemented")
            }
        })

    }
}