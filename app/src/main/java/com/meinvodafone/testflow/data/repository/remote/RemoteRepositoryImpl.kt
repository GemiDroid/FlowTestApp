package com.meinvodafone.testflow.data.repository.remote

import Constants.API_KEY
import com.meinvodafone.testflow.data.models.MoviesList
import com.meinvodafone.testflow.data.repository.remote.service.MoviesAPI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteRepositoryImpl(private val moviesApi: MoviesAPI) : IRemoteRepository {

    override fun getMoviesList(): Flow<MoviesList> = flow {
            emit(moviesApi.getAllMovies(API_KEY))
    }
}