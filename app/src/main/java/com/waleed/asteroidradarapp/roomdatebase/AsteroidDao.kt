package com.waleed.asteroidradarapp.roomdatebase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waleed.asteroidradarapp.Asteroid

@Dao
interface AsteroidDao {
    @Query("select * from asteroidsTable WHERE closeApproachDate >= :startDate ORDER BY closeApproachDate ASC")
    suspend fun getAllAsteroids(
        startDate: String,
    ): List<Asteroid>

    @Query("SELECT * FROM asteroidsTable WHERE closeApproachDate == :date")
    suspend fun getAsteroidsOfToday(
        date: String,
    ): List<Asteroid>


    @Query("SELECT * FROM asteroidsTable WHERE closeApproachDate > :startDate AND closeApproachDate < :endDate ORDER BY closeApproachDate ASC")
    suspend fun getAsteroidsOfTheWeek(
        startDate: String,
        endDate: String
    ): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: Asteroid)
}


