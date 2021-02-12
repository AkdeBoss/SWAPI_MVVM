package com.happinessinc.getshwifty.repo.api

import javax.inject.Inject

class LaunchEventsApiDataSource @Inject constructor(
    private val apiService: SpaceXApiService
): BaseDataSource() {

    suspend fun getLaunchEvents() = getResult { apiService.getAllLaunches() }
    suspend fun getSingleLaunchEvent(id: Int) = getResult { apiService.getSingleLaunch(id) }
}