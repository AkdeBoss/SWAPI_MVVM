package com.happinessinc.getshwifty.repo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.happinessinc.getshwifty.repo.models.LaunchEvent

@Database(entities = [LaunchEvent::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun launchEventDao(): LaunchEventDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "launchEvents")
                .fallbackToDestructiveMigration()
                .build()
    }

}