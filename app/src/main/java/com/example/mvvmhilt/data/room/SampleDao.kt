package com.example.mvvmhilt.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmhilt.data.models.UserData

@Dao
interface SampleDao {

    //REPLACE will replace the old data if it has the same key
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: ArrayList<UserData>)

    // A FlowData stream from the DB to handle live continuous data
    @Query("SELECT * FROM sample_table ORDER BY name ASC")
    fun getUsers(): LiveData<List<UserData>>

    //Delete data from table
    @Query("DELETE FROM sample_table")
    suspend fun clear()
}