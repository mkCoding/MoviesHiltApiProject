package com.example.playground.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playground.data.model.AllMoviesItemModel

@Composable
fun MovieScreen(
    movieState: MovieState
){
    Column(
        modifier = Modifier
            .fillMaxSize()
           .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Screen Title
        Text(
            text = "All Movies",
            fontSize = 50.sp,
            modifier =  Modifier
                .wrapContentSize()
                .padding(top = 16.dp)
        )

        when(movieState){
            is MovieState.Loading ->{
                CircularProgressIndicator()
            }
            is MovieState.Success ->{
                val movieList = movieState.movieData
                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(movieList){item ->
                        Card(
                            elevation = CardDefaults.elevatedCardElevation(15.dp),
                            shape = RoundedCornerShape(40.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text("Title", color = Color.Gray, fontWeight = FontWeight.SemiBold, fontStyle = FontStyle.Italic)
                                Text("${item.title}", fontSize = 25.sp, modifier = Modifier.padding(bottom = 16.dp), textAlign = TextAlign.Center)

                                // Description
                                Text("Description", color = Color.Gray, fontWeight = FontWeight.SemiBold, fontStyle = FontStyle.Italic)
                                Text("${item.description}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 16.dp), textAlign = TextAlign.Center)

                                Text("Genre", color = Color.Gray, fontWeight = FontWeight.SemiBold, fontStyle = FontStyle.Italic)
                                Text("${item.genre?.getOrNull(0)}", fontSize = 25.sp, modifier = Modifier.padding(bottom = 16.dp))


                                Text("Director", color = Color.Gray, fontWeight = FontWeight.SemiBold, fontStyle = FontStyle.Italic)
                                Text("${item.director}", fontSize = 25.sp, modifier = Modifier.padding(bottom = 16.dp), textAlign = TextAlign.Center)
                            }

                        }

                    }
                }
            }
            is MovieState.Error-> {
                Text("Error Retrieving Data")
            }
            else -> {}
        }
    }

}


@Preview(showBackground = true)
@Composable
fun MovieScreenPreview(){
    MovieScreen(
        MovieState.Success(getAllMovieList()
        )
    )
}

fun getAllMovieList():List<AllMoviesItemModel>{
    val allMoviesList = listOf(
        AllMoviesItemModel(
            title = "Neon Requiem",
            description = "In a rain-soaked cyberpunk city, a retired assassin must come back for one final job to save his daughter.",
            director = "Lina Voss",
            genre = listOf("Sci-Fi", "Action", "Thriller"),
            release_date = "2025-11-14"
        ),
        AllMoviesItemModel(
            title = "The Last Lighthouse",
            description = "A lone keeper on a remote island discovers the light doesn’t guide ships — it calls something from the deep.",
            director = "Mateo Rivera",
            genre = listOf("Horror", "Mystery"),
            release_date = "2025-08-22"
        ),
        AllMoviesItemModel(
            title = "Velocity Dreams",
            description = "A street racer with amnesia enters the world’s most dangerous underground race to remember who he really is.",
            director = "Kai Zhao",
            genre = listOf("Action", "Drama"),
            release_date = "2025-06-06"
        ),
        AllMoviesItemModel(
            title = "Paper Planes & Paper Hearts",
            description = "Two kids from opposite sides of the world connect through paper planes that somehow travel thousands of miles.",
            director = "Aisha Rahman",
            genre = listOf("Family", "Adventure", "Drama"),
            release_date = "2025-04-18"
        ),
        AllMoviesItemModel(
            title = "Ghost in the Algorithm",
            description = "An AI therapist begins falling in love with a patient who may not actually exist.",
            director = "Elias North",
            genre = listOf("Romance", "Sci-Fi", "Drama"),
            release_date = "2025-09-05"
        )

    )

    return allMoviesList

}