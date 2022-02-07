package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.App.GLB_STATE
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities.FilmDetailsActivity
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.FilmItemListBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.KEYS
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.RetrofitClient
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.dateToString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmListAdapter(private var films: List<Film>, val context: Context) :
    RecyclerView.Adapter<FilmListAdapter.FilmViewHolder>() {
    // context required in order to fetch the dimensions for the images
    private var filmPosterWidth: Int =
        context.resources.getDimension(R.dimen.film_poster_list_width).toInt()
    private var filmPosterHeight: Int =
        context.resources.getDimension(R.dimen.film_poster_list_height).toInt()

    class FilmViewHolder(val binding: FilmItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        // TODO(change the binding to a method inside the holder, instead of directing binding in Adapter.onBindViewHolder)
        val tvTitle = binding.tvFilmTitle
        val tvReleaseDate = binding.tvReleaseDate
        val tvDirector = binding.tvDirector
        val ivFilmPoster = binding.ivFilmPoster
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        // This method may be called multiple times (for example when scrolling down the list)
        // Thus the retrieval of the dimensions for the images has been changed to be performed only when FilmListAdapter gets created.
        val binding =
            FilmItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        // load the info of the corresponding film (based on it's position on the RecyclerView)
        // on the corresponding holder
        val film = films[position]
        with(holder) {
            tvTitle.setText(film.title)
            tvDirector.setText(context.getString(R.string.directed_by_prefix) + " " + film.director)
            tvReleaseDate.setText(
                film.releaseDate?.run {
                context.getString(R.string.releasedOnPrefix) + ": " + dateToString(this)
                }
            )
            try {
                Picasso.get().load(film.imageURL)
                    .resize(filmPosterWidth, filmPosterHeight).centerCrop()
                    .into(ivFilmPoster)
            } catch (ex: IllegalArgumentException) {
                ivFilmPoster.setImageResource(R.drawable.ic_image_not_found)
            }

        }
        // set the listener to navigate to the details page of the film
        holder.itemView.setOnClickListener {
            val getByIdCall = RetrofitClient.instance.getFilmById(film.id as String)

            getByIdCall.enqueue(object : Callback<Film> {
                override fun onResponse(call: Call<Film>, response: Response<Film>) {
                    if (response.isSuccessful) {
                        val intent: Intent =
                            Intent(holder.itemView.context, FilmDetailsActivity::class.java).apply {
                                putExtra(
                                    KEYS.FILM,
                                    response.body() as Film
                                )
                                // the cast may not be needed
                            }
                        it.context.startActivity(intent)
                    } else {
                        Toast.makeText(
                            context,
                            "Unexpected error from the server while requesting the film.",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("Error GET movie/{id}: ", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<Film>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "Unexpected error while requesting the film.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Error fetching", t.toString())
                }
            })
        }

        // TODO Change deletion to be through the API
        // set delete mechanism
        holder.itemView.setOnLongClickListener {
            var result: Boolean = false
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Deleting film")
                .setMessage("About to delete film " + film.title)
                .setPositiveButton("Delete", { _, _ ->
                    // update the global state
                    GLB_STATE.removeFilm(film.id!!.toLong())
                    // get the new state and update the list of items of the adapter
                    // IMPORTANT: not altering the inner state of the adapter results on a malformed list
                    this.films = GLB_STATE.getAllFilms()
                    // notify the adapter so it updates the rendered list
                    this.notifyItemRemoved(position)
                    result = true
                })
                .setNegativeButton("Keep", null)
                .create().show()
            result
        }
    }

    override fun getItemCount(): Int = films.size

}