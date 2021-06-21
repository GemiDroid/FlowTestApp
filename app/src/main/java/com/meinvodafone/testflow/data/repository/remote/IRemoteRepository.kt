package com.meinvodafone.testflow.data.repository.remote

import com.meinvodafone.testflow.data.models.Movie
import com.meinvodafone.testflow.data.models.MoviesList
import kotlinx.coroutines.flow.Flow

interface IRemoteRepository {
    fun getMoviesList(): Flow<MoviesList>
}