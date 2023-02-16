package com.waleed.asteroidradarapp.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.waleed.asteroidradarapp.Asteroid
import com.waleed.asteroidradarapp.PictureOfDay
import com.waleed.asteroidradarapp.api.RetrofitObject
import com.waleed.asteroidradarapp.roomdatebase.AsteroidRepository
import com.waleed.asteroidradarapp.roomdatebase.AsteroidsRoomDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {
    private val asteroidsDatabase = AsteroidsRoomDatabase.getInstance(application)
    private val asteroidsRepository = AsteroidRepository(asteroidsDatabase)

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay


    private val _selectedAsteroid = MutableLiveData<Asteroid?>()
    val selectedAsteroid: LiveData<Asteroid?>
        get() = _selectedAsteroid

    init {
        pictureOfTheDay()
        allAsteroids()
    }

    fun allAsteroids() {
        viewModelScope.launch {
            try {
                _asteroids.value = asteroidsRepository.getAllAsteroids()

                if (_asteroids.value?.isEmpty() == true) {
                    asteroidsRepository.refreshAsteroids()
                    _asteroids.value = asteroidsRepository.getAllAsteroids()
                }
            } catch (ex: Exception) {
                Log.e("mainViewModel", "error : ${ex.message}")
            }
        }
    }

    fun todayAsteroids() {
        viewModelScope.launch {
            try {
                _asteroids.value = asteroidsRepository.getAsteroidOfToday()
            } catch (ex: Exception) {
                Log.e("mainViewModel", "error : ${ex.message}")
            }
        }
    }

    fun weekAsteroids() {
        viewModelScope.launch {
            try {
                _asteroids.value = asteroidsRepository.getWeekAsteroids()
            } catch (ex: Exception) {
                Log.e("mainViewModel", "error : ${ex.message}")
            }
        }
    }

    private fun pictureOfTheDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = RetrofitObject.retrofitServices.getPictureOfTheDay()
            } catch (ex: Exception) {
                Log.e("mainViewModel", "error : ${ex.message}")
            }

        }
    }

    fun showAsteroidInDetail(asteroid: Asteroid) {
        _selectedAsteroid.value = asteroid
    }

    fun doneShowAsteroidInDetail() {
        _selectedAsteroid.value = null
    }

}

class MainViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
