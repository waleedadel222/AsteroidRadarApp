package com.waleed.asteroidradarapp.api


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.waleed.asteroidradarapp.Constants.API_KEY
import com.waleed.asteroidradarapp.Constants.BASE_URL
import com.waleed.asteroidradarapp.PictureOfDay
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET(value = "/neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ResponseBody

    @GET(value = "planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("api_key") apiKey: String = API_KEY
    ): PictureOfDay

}


object RetrofitObject {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val retrofitServices = retrofit.create(ApiServices::class.java)
}