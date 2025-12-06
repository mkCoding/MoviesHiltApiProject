package com.example.playground.data.api

import com.example.playground.data.model.AllMoviesItemModel
import retrofit2.http.GET

interface MoviesApi{

    @GET(ApiDetails.ENDPOINT_MOVIES)
    suspend fun getAllMovies(): List<AllMoviesItemModel>

}