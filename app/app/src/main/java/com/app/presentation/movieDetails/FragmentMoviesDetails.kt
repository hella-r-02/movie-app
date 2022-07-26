package com.app.presentation.movieDetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.databinding.FragmentMoviesDetailsBinding
import com.app.domain.model.MovieDetails
import com.app.presentation.MainActivity
import com.app.presentation.movieDetails.MovieState.*
import com.app.presentation.movieDetails.viewModel.MoviesDetailsViewModel
import com.bumptech.glide.Glide

class FragmentMoviesDetails : Fragment() {
    private lateinit var adapter: ActorsListAdapter
    private var movieId: Int? = null
    private lateinit var viewModel: MoviesDetailsViewModel
    private lateinit var binding: FragmentMoviesDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).getMovieDetailsViewModel()
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

        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesDetailsBinding.bind(view)
        viewModel.loadMovie(movieId!!)
        viewModel.liveDataState.observe(
            this.viewLifecycleOwner, this::setState
        )
        binding.llBackButton.setOnClickListener {
            onBackClickListener?.onClickBack()
        }
    }

    private fun setState(state: MovieState) {
        when (state) {
            is DefaultState -> state.movie?.let { loadData(it) }
            is ErrorState -> showErrorDialog()
        }
    }

    private fun showErrorDialog() {
        binding.pbLoadingMovie.visibility = View.INVISIBLE
        AlertDialog.Builder(
            ContextThemeWrapper(requireContext(), R.style.AlertDialogCustom)
        )
            .setCancelable(false)
            .setMessage(R.string.error_loading_dialog)
            .setNegativeButton("ok") { _, _ -> onBackClickListener?.onClickBack() }
            .show()
    }

    @SuppressLint("SetTextI18n")
    private fun loadData(movie: MovieDetails) {
        setVisibleTitle()
        binding.pbLoadingMovie.visibility = View.GONE
        setAdapterForRecyclerView(movie = movie)
        Glide.with(requireView())
            .load(movie.imageUrl)
            .fitCenter()
            .into(binding.ivPoster)
        binding.tvPg.text = movie.pgAge.toString() + "+"
        binding.tvMovieName.text = movie.title
        binding.tvGenre.text = movie.genres.joinToString { it.name }
        loadStars(movie = movie)
        binding.tvReview.text =
            movie.reviewCount.toString() + if (movie.reviewCount > 1) " reviews" else " review"
        binding.tvStoryline.text = movie.storyLine
        if (movie.actors.isEmpty()) {
            binding.tvCastTitle.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.setNullMovie()
    }

    private fun setVisibleTitle() {
        binding.llStars.visibility = View.VISIBLE
        binding.tvStorylineTitle.visibility = View.VISIBLE
        binding.tvCastTitle.visibility = View.VISIBLE
        binding.tvReview.visibility = View.VISIBLE
    }

    private fun loadStars(movie: MovieDetails) {
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

    private fun setAdapterForRecyclerView(movie: MovieDetails) {
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