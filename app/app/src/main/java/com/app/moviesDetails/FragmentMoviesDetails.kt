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
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.data.models.Movie
import com.bumptech.glide.Glide

class FragmentMoviesDetails : Fragment() {
    private lateinit var adapter: ActorsListAdapter
    private var recycler: RecyclerView? = null
    private var movie: Movie? = null

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
        movie = args?.get("movie") as Movie

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
        setAdapterForRecyclerView(view)

        val poster = view.findViewById<ImageView>(R.id.iv_poster)
        Glide.with(view)
            .load(movie?.poster)
            .fitCenter()
            .into(poster)
        val pg = view.findViewById<TextView>(R.id.tv_pg)
        pg.text = movie?.pg
        val name = view.findViewById<TextView>(R.id.tv_movie_name)
        name.text = movie?.name
        val genre = view.findViewById<TextView>(R.id.tv_genre)
        genre.text = movie?.genre
        val stars: List<ImageView> = listOf(
            view.findViewById(R.id.iv_star_1),
            view.findViewById(R.id.iv_star_2),
            view.findViewById(R.id.iv_star_3),
            view.findViewById(R.id.iv_star_4),
            view.findViewById(R.id.iv_star_5)
        )
        stars.forEachIndexed { index, star ->
            val colorId = if (movie?.rating!! - 1 > index) R.color.pink else R.color.gray
            ImageViewCompat.setImageTintList(
                star, ColorStateList.valueOf(
                    ContextCompat.getColor(star.context, colorId)
                )
            )
        }

        val reviews = view.findViewById<TextView>(R.id.tv_review)
        reviews.text = movie?.countOfReviews + " Reviews"
        val storyline = view.findViewById<TextView>(R.id.tv_storyline)
        storyline.text = movie?.storyline
    }

    private fun setAdapterForRecyclerView(view: View) {
        adapter = ActorsListAdapter()
        adapter.submitList(movie?.actors)
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