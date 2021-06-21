package com.meinvodafone.testflow.data.repository.remote.service

import com.meinvodafone.testflow.data.models.MoviesList
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {
    @GET("mostpopular/v2/viewed/7.json")
    suspend fun getAllMovies(@Query("api-key") apiKey : String) : MoviesList
}
