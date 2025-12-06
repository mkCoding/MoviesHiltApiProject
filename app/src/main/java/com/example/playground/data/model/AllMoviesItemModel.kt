package com.example.playground.data.model

data class AllMoviesItemModel(
    val description: String? = "",
    val director: String? = "",
    val genre: List<String?>? = listOf(),
    val release_date: String? = "",
    val title: String? = ""
)