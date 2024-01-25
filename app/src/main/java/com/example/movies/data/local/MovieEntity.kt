package com.example.movies.data.local

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity
data class MovieEntity(
    @PrimaryKey
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
    val page: Int ?= 0,

    )

data class GenresList(
    val id: Int?,
    val name:String
)