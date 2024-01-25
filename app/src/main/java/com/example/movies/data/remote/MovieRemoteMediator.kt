package com.example.movies.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movies.APIService.MovieApi
import com.example.movies.data.local.MovieDatabase
import com.example.movies.data.local.MovieEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediatorR(
    private val movieDatabase: MovieDatabase,
    private val movieApi: MovieApi
): RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        (lastItem.id?.div(state.config.pageSize))?.plus(1)
                    }
                }
            }

            delay(2000L)
            val moviesList = loadKey?.let {
                movieApi.getTodos(
                    page = it,
                )
            }

            movieDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    movieDatabase.dao.clearAll()
                }
                val beerEntities = moviesList!!.map {
                    it.toMovieEntity()
                }
                movieDatabase.dao.upsertAll(beerEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = moviesList!!.isEmpty()
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}