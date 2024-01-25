package com.example.movies.data.remote

import com.example.movies.data.local.MovieEntity
import com.example.movies.model.MovieList

fun MovieDto.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id=id,
        title=title,
        original_language=original_language,
        poster_path = poster_path
    )
}

fun MovieEntity.toMovie():MovieList{
     return MovieList(
        id=id,
        title=title,
        original_language=original_language,
        poster_path = poster_path
    )
}