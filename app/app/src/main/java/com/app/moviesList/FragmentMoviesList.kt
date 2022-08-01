package com.app.moviesList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.data.MovieRepositoryImpl
import com.app.databinding.FragmentMoviesListBinding
import com.app.model.Movie
import com.app.moviesList.viewModel.MoviesListViewModel
import com.app.moviesList.viewModel.MoviesListViewModelFactory

class FragmentMoviesList : Fragment() {

    private lateinit var adapter: MoviesListAdapter
    private lateinit var viewModel: MoviesListViewModel
    private lateinit var binding: FragmentMoviesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MoviesListViewModelFactory(MovieRepositoryImpl(requireContext()))
        )[MoviesListViewModel::class.java]
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
        viewModel.liveDataMovies.observe(
            this.viewLifecycleOwner
        ) { movies -> adapter.submitList(movies) }
        viewModel.loadMovies()
    }

    private fun setAdapterForRecyclerView() {
        adapter = MoviesListAdapter { movieData ->
            itemClickListener?.onMovieSelected(movieData)
        }
        val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.rvMovies.layoutManager = layoutManager
        binding.rvMovies.adapter = adapter
    }

    interface MoviesListItemClickListener {
        fun onMovieSelected(movie: Movie)
    }

}