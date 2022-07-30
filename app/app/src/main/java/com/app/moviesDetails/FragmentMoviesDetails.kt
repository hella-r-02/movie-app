package com.app.moviesDetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.data.JsonMovieRepository
import com.app.model.Movie
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class FragmentMoviesDetails : Fragment() {
    private lateinit var adapter: ActorsListAdapter
    private var recycler: RecyclerView? = null
    private var movieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var onBackClickListener: MoviesDetailsButtonClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesDetailsButtonClickListener) {
            onBackClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = this.arguments
        movieId = args?.get("movie_id") as Int

        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)
        val backButton = view.findViewById<LinearLayout>(R.id.ll_back_button)
        backButton.setOnClickListener {
            onBackClickListener?.onClickBack()
        }
        return view
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val repository = JsonMovieRepository(requireContext())
            val movie = repository.loadMovie(movieId = movieId!!)
            if (movie == null) {
                Toast.makeText(requireContext(), "Movie loading error", Toast.LENGTH_LONG).show()
            }
            setAdapterForRecyclerView(view, movie = movie!!)
            loadData(view = view, movie = movie)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadData(view: View, movie: Movie) {
        val poster = view.findViewById<ImageView>(R.id.iv_poster)
        Glide.with(view)
            .load(movie.detailImageUrl)
            .fitCenter()
            .into(poster)
        view.findViewById<TextView>(R.id.tv_pg).text = movie.pgAge.toString() + "+"
        view.findViewById<TextView>(R.id.tv_movie_name).text = movie.title
        view.findViewById<TextView>(R.id.tv_genre).text = movie.genres.joinToString { it.name }
        loadStars(view = view, movie = movie)
        view.findViewById<TextView>(R.id.tv_review).text =
            movie.reviewCount.toString() + " Reviews"
        view.findViewById<TextView>(R.id.tv_storyline).text = movie.storyLine
        if (movie.actors.isEmpty()) {
            view.findViewById<TextView>(R.id.tv_cast_title).visibility = View.INVISIBLE
        }
    }

    private fun loadStars(view: View, movie: Movie) {
        val stars: List<ImageView> = listOf(
            view.findViewById(R.id.iv_star_1),
            view.findViewById(R.id.iv_star_2),
            view.findViewById(R.id.iv_star_3),
            view.findViewById(R.id.iv_star_4),
            view.findViewById(R.id.iv_star_5)
        )
        stars.forEachIndexed { index, star ->
            val colorId = if (movie.rating - 1 > index) R.color.pink else R.color.gray
            ImageViewCompat.setImageTintList(
                star, ColorStateList.valueOf(
                    ContextCompat.getColor(star.context, colorId)
                )
            )
        }
    }

    private fun setAdapterForRecyclerView(view: View, movie: Movie) {
        adapter = ActorsListAdapter()
        adapter.submitList(movie.actors)
        recycler = view.findViewById(R.id.rv_actors)
        val layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.HORIZONTAL, false)
        recycler?.layoutManager = layoutManager
        recycler?.adapter = adapter
    }

    override fun onDetach() {
        recycler = null
        super.onDetach()
    }

    interface MoviesDetailsButtonClickListener {
        fun onClickBack()
    }
}