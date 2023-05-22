package com.example.mvvmhilt.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmhilt.interfaces.SampleDao
import com.example.mvvmhilt.models.NetworkData

/**
 * Declare all entities that exists in the database
 */
@Database(
    entities = [
        NetworkData::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class Database : RoomDatabase() {
    abstract fun getDao(): SampleDao
}