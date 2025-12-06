package com.example.playground.presentation

import android.graphics.Movie
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.data.model.AllMoviesItemModel
import com.example.playground.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val allMoviesRepository: MoviesRepository
): ViewModel(){

    // variable that will hold data that will be passed to UI
    private val _moviesState = MutableStateFlow<MovieState>(MovieState.Loading)
    val moviesState: StateFlow<MovieState> = _moviesState

    init {
        loadMovieData()
    }
    // loadData
    fun loadMovieData(){
        _moviesState.value = MovieState.Loading

        viewModelScope.launch {
            try {
                val movieData = allMoviesRepository.getAllMovies()
                _moviesState.value = MovieState.Success(movieData)
            }catch (e:Exception){
                _moviesState.value = MovieState.Error("${e.message}")
            }
        }

    }
}

sealed class MovieState {
    object Loading: MovieState()
    data class Success(val movieData:List<AllMoviesItemModel>):MovieState()
    data class Error (val message:String): MovieState()
}