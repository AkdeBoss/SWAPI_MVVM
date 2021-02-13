package com.happinessinc.getshwifty.repo

import com.happinessinc.getshwifty.repo.api.ApiResponse
import com.happinessinc.getshwifty.repo.api.LaunchEventsApiDataSource
import com.happinessinc.getshwifty.repo.db.LaunchEventDao
import com.happinessinc.getshwifty.repo.models.LaunchEvent
import javax.inject.Inject

class LaunchRepo @Inject constructor(
    private val remoteDataSource: LaunchEventsApiDataSource,
    private val localDataSource: LaunchEventDao
) {
    suspend fun getEvent(id: String):ApiResponse<LaunchEvent> {
        var data: ApiResponse<LaunchEvent> = ApiResponse.loading()
            data=remoteDataSource.getSingleLaunchEvent(id)
        return data
    }

    suspend fun getEvents():ApiResponse<List<LaunchEvent>>{
        val cachedData= localDataSource.getAllEvents().value ?: listOf()

        var data: ApiResponse<List<LaunchEvent>> = ApiResponse.loading()
        data = if(cachedData==null || cachedData.isEmpty()){
            remoteDataSource.getLaunchEvents()
        }else{
            ApiResponse.success(cachedData)
        }
        return data
    }

    fun getLaunches()  = getData(
    databaseQuery = { localDataSource.getAllEvents() },
    networkCall = { remoteDataSource.getLaunchEvents() },
    saveCallResult = { localDataSource.insertAll(it) }
    )
    }
