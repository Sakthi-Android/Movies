package com.example.movies.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.movies.APIService.MoviesRepository
import com.example.movies.data.local.MovieEntity
import com.example.movies.data.remote.toMovie
import com.example.movies.model.GetMoviesResponse
import com.example.movies.model.MovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    pager: Pager<Int,MovieEntity>
): ViewModel() {

    val moviePagingFlow=pager.flow.map { pagingData->
        pagingData.map { it.toMovie() }
    }
        .cachedIn(viewModelScope)
    private val _todoList = mutableStateListOf<List<MovieList>>()
    private var _movieDetails = mutableStateListOf <MovieList>()
    var errorMessage: String by mutableStateOf("")
    val todoList: SnapshotStateList<List<MovieList>>
        get() = _todoList
    val movieDetails:List<MovieList>
        get() = _movieDetails

    private  val API_KEY_VALUE = "4b1f833fc780beee73f6747e6e52dcf5"
    private val LANGUAGE = "en"

//    fun getPopularMovies(page: Int = 1) {
//        MoviesRepository.api.getTodos(page = page)
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            _todoList.add(responseBody.movies)
//                            Log.d("Repository", "Movies: ${responseBody.movies }")
//                        } else {
//                            Log.d("Repository", "Failed to get response")
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    Log.e("Repository", t.localizedMessage, t)
//                }
//            })
//    }
    fun getDetailMovies(movieId: Int) {
        MoviesRepository.api.getMovieDetails(id=movieId)
            .enqueue(object : Callback<MovieList> {
                override fun onResponse(
                    call: Call<MovieList>,
                    response: Response<MovieList>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            _movieDetails.add(responseBody)
                            Log.d("Details Repository", "Movies: $responseBody")
                        } else {
                            Log.d("Details Repository", "Failed to get response")
                        }
                    }else{
                        println("callApi")

                    }
                }

                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                    Log.e("Repository", t.localizedMessage, t)

                }
            })
    }

}