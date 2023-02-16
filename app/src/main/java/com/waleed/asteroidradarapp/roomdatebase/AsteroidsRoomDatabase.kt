package com.waleed.asteroidradarapp.roomdatebase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.waleed.asteroidradarapp.Asteroid


@Database(entities = [Asteroid::class], version =1, exportSchema = false)
abstract class AsteroidsRoomDatabase : RoomDatabase() {

    abstract val asteroidDao: AsteroidDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidsRoomDatabase? = null

        fun getInstance(context: Context): AsteroidsRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidsRoomDatabase::class.java,
                        "asteroids Room Database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}