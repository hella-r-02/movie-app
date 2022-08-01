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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.data.MovieRepositoryImpl
import com.app.databinding.FragmentMoviesDetailsBinding
import com.app.model.Movie
import com.app.moviesDetails.viewModel.MovieState
import com.app.moviesDetails.viewModel.MovieState.*
import com.app.moviesDetails.viewModel.MoviesDetailsViewModel
import com.app.moviesDetails.viewModel.MoviesDetailsViewModelFactory
import com.bumptech.glide.Glide

class FragmentMoviesDetails : Fragment() {
    private lateinit var adapter: ActorsListAdapter
    private var movieId: Int? = null
    private lateinit var viewModel: MoviesDetailsViewModel
    private lateinit var binding: FragmentMoviesDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MoviesDetailsViewModelFactory(MovieRepositoryImpl(requireContext()))
        )[MoviesDetailsViewModel::class.java]
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
        binding = FragmentMoviesDetailsBinding.bind(view)
        viewModel.liveDataState.observe(
            this.viewLifecycleOwner, this::setState
        )
        viewModel.loadMovie(movieId!!)
    }

    private fun setState(state: MovieState) {
        when (state) {
            is DefaultState -> state.movie?.let { loadData(it) }
            is ErrorState -> Toast.makeText(
                requireContext(),
                "Movie loading error",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadData(movie: Movie) {
        setAdapterForRecyclerView(movie = movie)
        Glide.with(requireView())
            .load(movie.detailImageUrl)
            .fitCenter()
            .into(binding.ivPoster)
        binding.tvPg.text = movie.pgAge.toString() + "+"
        binding.tvMovieName.text = movie.title
        binding.tvGenre.text = movie.genres.joinToString { it.name }
        loadStars(movie = movie)
        binding.tvReview.text = movie.reviewCount.toString() + " Reviews"
        binding.tvStoryline.text = movie.storyLine
        if (movie.actors.isEmpty()) {
            binding.tvCastTitle.visibility = View.INVISIBLE
        }
    }

    private fun loadStars(movie: Movie) {
        val stars: List<ImageView> = listOf(
            binding.ivStar1,
            binding.ivStar2,
            binding.ivStar3,
            binding.ivStar4,
            binding.ivStar5
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

    private fun setAdapterForRecyclerView(movie: Movie) {
        adapter = ActorsListAdapter()
        adapter.submitList(movie.actors)
        val layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.HORIZONTAL, false)
        binding.rvActors.layoutManager = layoutManager
        binding.rvActors.adapter = adapter
    }

    interface MoviesDetailsButtonClickListener {
        fun onClickBack()
    }
}