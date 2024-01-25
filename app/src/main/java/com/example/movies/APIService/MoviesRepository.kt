package com.example.movies.APIService

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.movies.data.local.MovieDatabase
import com.example.movies.data.local.MovieEntity
import com.example.movies.data.remote.MovieRemoteMediatorR
import com.example.movies.model.GetMoviesResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object MoviesRepository {
    
        const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val TMDB_IMAGE_URL = "https://image.tmdb.org/t/p/w300/"

        private const val API_KEY_PARAM = "api_key"
        private const val API_KEY_VALUE = "4b1f833fc780beee73f6747e6e52dcf5"

        private const val LANGUAGE_REQUEST_PARAM = "language"
        private const val LANGUAGE = "en"

        private const val PAGE_REQUEST_PARAM = "page"
        val api: MovieApi

        init {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            api = retrofit.create(MovieApi::class.java)

        }
    }
