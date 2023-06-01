package com.example.mvvmhilt.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anushka.tmdbclient.data.model.tvshow.TvShow

/**
 * Declare all entities that exists in the database
 */
@Database(
    entities = [
        TvShow::class
    ],
    version = 1,
    exportSchema = false
)

abstract class Database : RoomDatabase() {
    abstract fun getTvShowDao(): TvShowDao
}