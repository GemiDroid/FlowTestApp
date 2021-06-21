package com.meinvodafone.testflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meinvodafone.testflow.data.models.Movie
import com.meinvodafone.testflow.data.repository.remote.IRemoteRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieListViewModel(private val repository: IRemoteRepository) : ViewModel() {

    fun getMovies() {
        viewModelScope.launch {
            repository.getMoviesList()
                .onStart {
                    _moviesUiStateFlow.value = MovieState.LOADING
                    _moviesUiSharedFlow.emit(MovieState.LOADING)
                }
                .catch {
                    _moviesUiStateFlow.value = MovieState.ERROR(exception = it)
                    _moviesUiSharedFlow.emit(MovieState.ERROR(it))
                }
                .collect {
                    _moviesUiStateFlow.value = MovieState.SUCCESS(it.moviesList)
                    _moviesUiSharedFlow.emit(MovieState.SUCCESS(it.moviesList))
                }
        }
    }

    private val _moviesUiStateFlow = MutableStateFlow<MovieState>(MovieState.EMPTY)
    // replay: items count when new subscriber/consumer is join streaming,
    // DEFAULT_BUFFER_SIZE: returns the default buffer size when working with buffered streams.
    // BufferOverflow.SUSPEND: When overflow happens, so Suspend on buffer..

    private val _moviesUiSharedFlow =
        MutableSharedFlow<MovieState>(replay = 1, DEFAULT_BUFFER_SIZE, BufferOverflow.SUSPEND)

    val moviesUiStateFlow = _moviesUiStateFlow
    val moviesUiSharedFlow = _moviesUiSharedFlow

    init {
        getMovies()
    }
}

sealed class MovieState {
    data class SUCCESS(val moviesList: List<Movie>) : MovieState()
    data class ERROR(val exception: Throwable) : MovieState()
    object LOADING : MovieState()
    object EMPTY : MovieState()
}
