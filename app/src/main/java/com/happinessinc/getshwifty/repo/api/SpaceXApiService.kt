package com.happinessinc.getshwifty.repo.api

import com.happinessinc.getshwifty.repo.models.LaunchEvent
import com.happinessinc.getshwifty.repo.models.SpaceXrespone
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceXApiService {
    @GET("v4/launches")
    suspend fun getAllLaunches() : Response<SpaceXrespone>

    @GET("v4/launches{id}")
    suspend fun getSingleLaunch(@Path("id") id: Int): Response<LaunchEvent>
}