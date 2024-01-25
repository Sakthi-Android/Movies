package com.example.movies.model

import android.util.Log
import androidx.room.TypeConverter

data class MovieList(
    val id: Int? = null,
    val title: String? = "",
    val poster: String? = "",
    val release_date: String? = "",
    val backdrop_path:String?="",
    val poster_path:String?="",
    val overview:String?="",
    val popularity:String?="",
    val original_language:String?="",
    val vote_average:Double?=null,
    val genres:List<genresList>?= null,
    val page:Int= 0,
)
data class GetMoviesResponse(
     val page: Int,
    val movies: List<MovieList>,
   val pages: Int
)

data class genresList(
    val id: Int?,
    val name:String
)
//class genresListTypeConverter {
//    val movies: MovieList = MovieList()
//
//    @TypeConverter
//    fun toDetailList(value: String): MovieList {
//        Log.d("DBINFO_FROMJSON","Extracted>>${value}") /* just for demonstration */
//        return Gson().fromJson(value,MovieList::class.java)
//    }
//    @TypeConverter
//    fun fromDetailList(value: MovieList): String {
//        return  Gson().toJson(value)
//    }
//}