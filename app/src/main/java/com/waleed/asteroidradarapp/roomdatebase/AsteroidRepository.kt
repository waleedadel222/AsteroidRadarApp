package com.waleed.asteroidradarapp.roomdatebase


import com.waleed.asteroidradarapp.Asteroid
import com.waleed.asteroidradarapp.api.getTodayDate
import com.waleed.asteroidradarapp.api.getWeekDate
import com.waleed.asteroidradarapp.api.parseAsteroidsJsonResult
import com.waleed.asteroidradarapp.api.RetrofitObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject

class AsteroidRepository(private val asteroidDbObject: AsteroidsRoomDatabase) {

    suspend fun refreshAsteroids(

    ) {
        return withContext(Dispatchers.IO) {
            val asteroidResponseBody: ResponseBody = RetrofitObject.retrofitServices.getAsteroids(
                getTodayDate(),
                getWeekDate()
            )
            val asteroidsList = parseAsteroidsJsonResult(JSONObject(asteroidResponseBody.string()))
            asteroidDbObject.asteroidDao.insertAll(*asteroidsList.asDatabaseModel())
        }
    }


    suspend fun getAllAsteroids(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            asteroidDbObject.asteroidDao.getAllAsteroids(getTodayDate()).asDomainModel()
        }
    }

    suspend fun getAsteroidOfToday(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            asteroidDbObject.asteroidDao.getAsteroidsOfToday(
                getTodayDate()
            ).asDomainModel()
        }
    }


    suspend fun getWeekAsteroids(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            asteroidDbObject.asteroidDao.getAsteroidsOfTheWeek(
                getTodayDate(),
                getWeekDate()
            ).asDomainModel()
        }
    }


}

fun List<Asteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun List<Asteroid>.asDatabaseModel(): Array<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}
