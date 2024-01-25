package com.example.movies.ui

sealed class MovieScreenNavs(val route:String) {
    object MainScreen: MovieAppScreen("main_screen")
    object DetailScreen: MovieAppScreen("detail_screen")

    fun withArgs(vararg args:Int): String {
        return buildString {
            append(route)
            args.forEach {
                    arg -> append("/$arg")
            }
        }
    }
}
