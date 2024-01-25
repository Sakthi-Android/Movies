package com.example.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.movies.model.MovieList
import com.example.movies.ui.MovieNavigation
import com.example.movies.ui.MovieScreenNavs
import com.example.movies.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MovieNavigation()
            }
        }
    }
}

@Composable
fun MovieScreen(
    movies: LazyPagingItems<MovieList>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                (movies.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    if (movies.loadState.refresh is LoadState.Loading) {
//        CircularProgressIndicator(
//            modifier = Modifier.align()
//        )
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    viewModel: MovieViewModel,
    navController: NavHostController,
    movies: LazyPagingItems<MovieList>
) {
//    LaunchedEffect(Unit, block = {
//        viewModel.getPopularMovies()
//    })
//    val movies=viewModel.moviePagingFlow.collectAsLazyPagingItems()
    val context = LocalContext.current
    LaunchedEffect(key1 = movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (movies.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text("Popular Movies")
                    }
                },
            )
        },
        content = {
            val popularList = mutableStateListOf<MovieList>()
            if (viewModel.errorMessage.isEmpty()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (movies.loadState.refresh is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(CenterHorizontally)
                        )
                    } else {
                        LazyVerticalGrid(
                            GridCells.Fixed(2),
                            modifier = Modifier.fillMaxHeight(),
                        ) {
                            viewModel.todoList.forEachIndexed { _, movieLists ->
                                movieLists.forEachIndexed { _, movieList ->
                                    popularList.add(movieList)
                                }
                            }
                            items(popularList) { movie ->
                                Column {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(4.dp)
                                                .clickable {
                                                    navController.navigate(
                                                        MovieScreenNavs.DetailScreen.withArgs(
                                                            movie.id
                                                        )
                                                    )
                                                }
                                        ) {
                                            AsyncImage(
                                                modifier = Modifier.fillMaxSize(),
                                                model = "https://image.tmdb.org/t/p/w300/${movie.poster_path}",
                                                contentDescription = "Movie Image",
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }
                            }

                            item {
                                if (movies.loadState.append is LoadState.Loading) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            } else {
                Text(
                    viewModel
                        .errorMessage
                )
            }
        }
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(id: Int?, navController: NavHostController, viewModel: MovieViewModel) {
    var details = MovieList()
    val genres: StringBuilder = StringBuilder()
    LaunchedEffect(Unit, block = {
        if (id != null) {
            viewModel.getDetailMovies(movieId = id)
        }
    })
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text("Movie Details")
                    }
                },
            )
        },
        content = {
            viewModel.movieDetails.forEachIndexed { _, movieLists ->
                details = movieLists
                println(details)
            }


            if (viewModel.errorMessage.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        modifier = Modifier.size(400.dp),
                        model = "https://image.tmdb.org/t/p/w300/${details.poster_path}",
                        contentDescription = "Movie Image",
                        contentScale = ContentScale.FillHeight,
                        alignment = Alignment.Center

                    )
                    Text(
                        "${details.title}",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Filled.DateRange,
                            contentDescription = "${details.release_date}"
                        )
                        Text(
                            "${details.release_date}",
                            Modifier.padding(start = 8.dp),
                        )
                        Text(
                            "(${details.original_language})",
                            Modifier.padding(start = 2.dp),
                        )
                    }
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "${details.vote_average}"
                        )
                        if (genres.isEmpty())
                            details.genres?.forEach {
                                genres.append(it.name)
                            }
                        Text(
                            "${details.vote_average}/10",
                            Modifier.padding(start = 8.dp),
                        )
                        Text(
                            "($genres)",
                            Modifier.padding(start = 2.dp),
                        )
                    }
                    Text(
                        "Overview",
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "${details.overview}",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                Text(
                    viewModel
                        .errorMessage
                )
            }
        })

}

@Composable
fun TitleRow(movie: MovieList?) {
    Row(Modifier.background(MaterialTheme.colorScheme.secondary)) {
        Column(
            Modifier
                .weight(6F, true)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${movie?.title}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(10.dp),
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center
            )
            Row(Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                Column(
                    Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ReleaseDate(movie, MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f))
                }
                Column(
                    Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MovieRating(movie, MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f))
                }
            }
        }
    }
}

@Composable
fun ReleaseDate(movie: MovieList?, tint: Color) {
    Row(horizontalArrangement = Arrangement.Center) {
        Icon(
            Icons.Filled.DateRange,
            contentDescription = "${movie?.release_date}",
            tint = tint
        )
        Text(
            "${movie?.release_date}",
            Modifier.padding(start = 8.dp),
            color = tint
        )
    }
}

@Composable
fun MovieRating(movie: MovieList?, tint: Color) {
    Row(horizontalArrangement = Arrangement.Center) {
        Icon(
            Icons.Filled.Star,
            contentDescription = "${movie?.popularity}",
            tint = tint
        )
        Text(
            "${movie?.popularity} / 10",
            Modifier.padding(start = 8.dp),
            color = tint
        )
    }
}

@Composable
fun LoadingIndicator() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}