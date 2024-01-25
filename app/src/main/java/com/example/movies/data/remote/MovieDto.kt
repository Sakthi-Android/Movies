package com.example.movies.data.remote


data class MovieDto(
    val id: Int? = null,
    val title: String? = "",
    val poster: String? = "",
    val release_date: String? = "",
    val backdrop_path: String? = "",
    val poster_path: String? = "",
    val overview: String? = "",
    val popularity: String? = "",
    val original_language: String? = "",
    val vote_average: Double? = null,
    val genres: List<genresList>? = listOf(),
    val page: Int = 0,

    )
data class genresList(
    val id: Int?,
    val name:String
)