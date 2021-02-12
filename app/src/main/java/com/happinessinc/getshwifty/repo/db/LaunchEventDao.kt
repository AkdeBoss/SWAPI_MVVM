package com.happinessinc.getshwifty.repo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.happinessinc.getshwifty.repo.models.LaunchEvent

@Dao
interface LaunchEventDao {

    @Query("SELECT * FROM launchevents")
    fun getAllEvents() : LiveData<List<LaunchEvent>>

    @Query("SELECT * FROM launchevents WHERE id = :id")
    fun getEvent(id: Int): LiveData<LaunchEvent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<LaunchEvent>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: LaunchEvent)

    @Query("DELETE  FROM launchevents")
    suspend fun nukeALL()


}