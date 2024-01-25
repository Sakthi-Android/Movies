package com.example.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieEntity::class],
    version = 1,exportSchema = false)

abstract class MovieDatabase :RoomDatabase(){
    abstract val dao:MovieDao
}