package com.example.weatherapp.data

import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.common.Constants
import com.example.weatherapp.data.network.OpenWeatherAPI
import com.example.weatherapp.data.network.WeatherResult
import com.example.weatherapp.data.network.WeeklyForcastResult
import com.example.weatherapp.data.network.util.NetworkUtil

class WeatherRepository {

    suspend fun getWeatherByZip(zipCode: Int, country: String): WeatherResult {
        val weatherResult: WeatherResult
        weatherResult = try {
            WeatherResult.Success(
                OpenWeatherAPI.api.getWeatherByZip(
                    NetworkUtil.concat(zipCode, country),
                    Constants.API_KEY
                )
            )
        } catch (e: Exception) {
            WeatherResult.Error(e)
        }
        return weatherResult
    }

    suspend fun getWeeklyWeatherForecast(
        zipCode: Int,
        country: String
    ): WeeklyForcastResult {
        val weatherResult: WeeklyForcastResult
        weatherResult = try {
            WeeklyForcastResult.Success(
                OpenWeatherAPI.api.getWeeklyForcastByZip(
                    NetworkUtil.concat(zipCode, country),
                    Constants.API_KEY
                )
            )
        } catch (e: Exception) {
            WeeklyForcastResult.Error(e)
        }
        return weatherResult
    }
}