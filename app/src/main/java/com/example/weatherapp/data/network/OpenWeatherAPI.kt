package com.example.weatherapp.data.network

import com.example.weatherapp.common.Constants
import com.example.weatherapp.data.network.model.WeatherByZipResponse
import com.example.weatherapp.data.network.model.WeeklyForcastResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object OpenWeatherAPI {
    interface Service {
        @GET("/data/2.5/weather/")
        suspend fun getWeatherByZip(
            @Query("zip", encoded = true) zipCode: String,
            @Query("APPID") appId: String
        ): WeatherByZipResponse

        @GET("/data/2.5/forecast/")
        fun getWeeklyForcastByZip(
            @Query("q", encoded = true) zipCodeWithCountryCode: String,
            apiKey: String
        ): WeeklyForcastResponse
    }


    val api: Service by lazy {
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Service::class.java)
    }
}