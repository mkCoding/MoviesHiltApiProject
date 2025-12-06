package com.example.playground.domain

import com.example.playground.data.api.MoviesApi
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi
){
    suspend fun getAllMovies() = this@MoviesRepository.moviesApi.getAllMovies()

}