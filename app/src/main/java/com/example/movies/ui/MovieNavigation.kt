package com.example.movies.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.DetailScreen
import com.example.movies.MovieListScreen
import com.example.movies.viewmodel.MovieViewModel

@Composable
fun MovieNavigation() {
    val navController = rememberNavController()
    val vm = hiltViewModel<MovieViewModel>()
    val movies = vm.moviePagingFlow.collectAsLazyPagingItems()

    NavHost(navController = navController, startDestination = MovieScreenNavs.MainScreen.route) {
        composable(route = MovieAppScreen.MainScreen.route) {
            MovieListScreen(viewModel = vm,navController = navController,movies)
        }
        composable(
            route = MovieAppScreen.DetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) {
                entry ->
            DetailScreen(entry.arguments?.getInt("id"), navController = navController, viewModel = vm)
        }
    }
}



