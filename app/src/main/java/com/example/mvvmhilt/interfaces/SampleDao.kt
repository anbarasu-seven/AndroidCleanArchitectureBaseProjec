package com.example.mvvmhilt.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.mvvmhilt.models.NetworkData

@Dao
interface SampleDao {

    //REPLACE will replace the old data if it has the same key
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: ArrayList<NetworkData>)

    // A FlowData stream from the DB to handle live continuous data
    @Query("SELECT * FROM sample_table ORDER BY name ASC")
    fun getOrderedNetworkDataFlow(): Flow<List<NetworkData>>

    //Delete data from table
    @Query("DELETE FROM sample_table")
    fun clearAll()
}