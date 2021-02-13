package com.happinessinc.getshwifty.repo.models

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

class SpaceXrespone : ArrayList<LaunchEvent>()

@Entity(tableName = "LaunchEvents")
data class LaunchEvent(
    @PrimaryKey
    @NonNull
    var id: String,
    var auto_update: Boolean?=null,
    var date_local: String?=null,
    var date_precision: String?=null,
    var date_unix: Int?=null,
    var date_utc: String?=null,
    var details: String?=null,
    @Embedded
    var fairings: Fairings?=null,
    var flight_number: Int?=null,
    var launch_library_id: String?=null,
    var launchpad: String?=null,
    @Embedded
    var links: Links?=null,
    var name: String?=null,
    var net: Boolean?=null,
    var rocket: String?=null,
    var static_fire_date_unix: Int?=null,
    var static_fire_date_utc: String?=null,
    var success: Boolean?=null,
    var tbd: Boolean?=null,
    var upcoming: Boolean?=null,
    var window: Int?=null
)


data class Fairings(
    var recovered: String?=null,
    var recovery_attempt: Boolean?=null,
    var reused: Boolean?=null
)

data class Links(
    var article: String?=null,
//    @Embedded
//    var flickr: Flickr,
    @Embedded
    var patch: Patch?=null,
    var presskit: String?=null,
    @Embedded
    var reddit: Reddit?=null,
    var webcast: String?=null,
    var wikipedia: String?=null,
    var youtube_id: String?=null
)

data class Flickr(
    var original: List<String>?=null,
    var small: List<Any>?=null
)

data class Patch(
    var large: String?=null,
    var small: String?=null
)

data class Reddit(
    var campaign: String?=null,
    var launch: String?=null,
    var media: String?=null,
    var recovery: String?=null
)