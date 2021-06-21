package com.meinvodafone.testflow.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.meinvodafone.testflow.R
import com.meinvodafone.testflow.data.models.Movie
import com.meinvodafone.testflow.presentation.viewmodel.MovieListViewModel
import com.meinvodafone.testflow.presentation.viewmodel.MovieState
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val movieListViewModel by viewModel<MovieListViewModel>()

   // private val movieListViewModel: MovieListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Launch this job inside activity scope start..
        lifecycleScope.launchWhenStarted {
            /**
             * Mapping from Flow into LiveData..
             **/
          /*  movieListViewModel._moviesUiStateFlow.asLiveData().observe(this@MainActivity, {
             })*/

            movieListViewModel.moviesUiStateFlow.collect {
                when (it) {
                    is MovieState.SUCCESS -> handleMoviesList(it.moviesList)
                    is MovieState.ERROR -> handleError(it.exception)
                    is MovieState.EMPTY ->{} // handle empty view
                    is MovieState.LOADING ->{} // show loading
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        menuInflater.inflate(R.menu.refresher, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun handleError(e: Throwable) {
        Snackbar.make(
            rec_movies,
            e.localizedMessage ?: getString(R.string.generic_error),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun handleMoviesList(moviesList: List<Movie>) {
        rec_movies.adapter = MoviesAdapter(moviesList) {}
    }

    fun refresh(item: MenuItem) {
        movieListViewModel.getMovies()
    }
}