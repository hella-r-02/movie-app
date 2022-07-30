package com.app.moviesList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.data.JsonMovieRepository
import com.app.model.Movie
import kotlinx.coroutines.launch

class FragmentMoviesList : Fragment() {

    private lateinit var adapter: MoviesListAdapter
    private var recycler: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        super.onViewCreated(view, savedInstanceState)
        val repository = JsonMovieRepository(requireContext())
        lifecycleScope.launch {
            val movies = repository.loadMovies()
            setAdapterForRecyclerView(view = view, movies = movies)
        }
    }

    private fun setAdapterForRecyclerView(view: View, movies: List<Movie>) {
        adapter = MoviesListAdapter { movieData ->
            itemClickListener?.onMovieSelected(movieData)
        }
        adapter.submitList(movies)
        recycler = view.findViewById(R.id.rv_movies)
        val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        recycler?.layoutManager = layoutManager
        recycler?.adapter = adapter
    }

    interface MoviesListItemClickListener {
        fun onMovieSelected(movie: Movie)
    }

}