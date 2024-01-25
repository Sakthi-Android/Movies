package com.example.movies.APIService

import android.database.Observable
import com.example.movies.data.remote.MovieDto
import com.example.movies.model.GetMoviesResponse
import com.example.movies.model.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {
    @GET("movie/popular")
    suspend fun getTodos(
        @Query("api_key") apiKey: String = "4b1f833fc780beee73f6747e6e52dcf5",
        @Query("page") page: Int
    ): List<MovieDto>

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path("id") id:Int,
        @Query("api_key") apiKey: String = "4b1f833fc780beee73f6747e6e52dcf5",
    ): Call<MovieList>
}