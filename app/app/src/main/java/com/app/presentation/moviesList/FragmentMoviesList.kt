package com.app.presentation.moviesList

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.databinding.FragmentMoviesListBinding
import com.app.domain.model.Movie
import com.app.presentation.MainActivity
import com.app.presentation.moviesList.viewModel.MoviesListViewModel

class FragmentMoviesList : Fragment() {

    private lateinit var adapter: MoviesListAdapter
    private lateinit var viewModel: MoviesListViewModel
    private lateinit var binding: FragmentMoviesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).getMovieListViewModel()
    }

    private var itemClickListener: MoviesListItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesListItemClickListener) {
            itemClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMoviesListBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setAdapterForRecyclerView()
        viewModel.loadMoviesAsFlow()
        viewModel.liveDataIsError.observe(viewLifecycleOwner, this::errorLoadingMovies)
        viewModel.liveDataMovies.observe(viewLifecycleOwner, this::updateListOfMovies)
        viewModel.loadMovies()
    }

    private fun errorLoadingMovies(isError: Boolean) {
        if (isError) {
            showErrorDialog()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateListOfMovies(newMoviesList: List<Movie>) {
        if (newMoviesList.isNotEmpty()) {
            binding.pbLoadingMovies.visibility = View.GONE
            adapter.submitList(newMoviesList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setAdapterForRecyclerView() {
        adapter = MoviesListAdapter({ item -> itemClickListener?.onMovieSelected(item) }, { item ->
            onClickLike(item)
        })
        val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.rvMovies.layoutManager = layoutManager
        binding.rvMovies.adapter = adapter
    }

    private fun showErrorDialog() {
        binding.pbLoadingMovies.visibility = View.INVISIBLE
        AlertDialog.Builder(
            ContextThemeWrapper(requireContext(), R.style.AlertDialogCustom)
        )
            .setCancelable(false)
            .setMessage(R.string.error_loading_dialog)
            .show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onClickLike(movie: Movie) {
        Log.e("MovieList", "like")
        viewModel.updateLike(movie)
    }

    interface MoviesListItemClickListener {
        fun onMovieSelected(movie: Movie)
    }
}