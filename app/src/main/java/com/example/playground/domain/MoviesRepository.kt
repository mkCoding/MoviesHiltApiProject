package com.example.playground.domain

import com.example.playground.data.api.APIEndpoints
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val apiEndpoints: APIEndpoints
){
    suspend fun getAllMovies() = apiEndpoints.getAllMovies()

}