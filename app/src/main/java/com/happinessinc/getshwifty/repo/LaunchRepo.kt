package com.happinessinc.getshwifty.repo

import androidx.lifecycle.LiveData
import com.happinessinc.getshwifty.repo.api.ApiResponse
import com.happinessinc.getshwifty.repo.api.LaunchEventsApiDataSource
import com.happinessinc.getshwifty.repo.db.LaunchEventDao
import javax.inject.Inject

class LaunchRepo @Inject constructor(
    private val remoteDataSource: LaunchEventsApiDataSource,
    private val localDataSource: LaunchEventDao
) {

    fun getSingleLaunch(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getEvent(id) },
        networkCall = { remoteDataSource.getSingleLaunchEvent(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getLaunches() = performGetOperation(
        databaseQuery = { localDataSource.getAllEvents() },
        networkCall = { remoteDataSource.getLaunchEvents() },
        saveCallResult = { localDataSource.insertAll(it) }
    )

    suspend fun clearLocalRepo()= localDataSource.nukeALL()
}