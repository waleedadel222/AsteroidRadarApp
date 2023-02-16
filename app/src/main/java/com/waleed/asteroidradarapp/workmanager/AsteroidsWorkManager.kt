package com.waleed.asteroidradarapp.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.waleed.asteroidradarapp.roomdatebase.AsteroidRepository
import com.waleed.asteroidradarapp.roomdatebase.AsteroidsRoomDatabase

import retrofit2.HttpException

class AsteroidsWorkManager(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {


    override suspend fun doWork(): Result {
        val asteroidDatabase = AsteroidsRoomDatabase.getInstance( applicationContext)
        val asteroidRepository = AsteroidRepository(asteroidDatabase)

        return try {
            asteroidRepository.refreshAsteroids()
            Result.success()
        } catch (exception: HttpException) {
            return Result.retry()
        }
    }
}