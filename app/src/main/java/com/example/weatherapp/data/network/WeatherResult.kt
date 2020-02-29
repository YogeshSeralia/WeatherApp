package com.example.weatherapp.data.network

import com.example.weatherapp.data.network.model.WeatherByZipResponse

sealed class WeatherResult {
    class Error(val exception:Exception) : WeatherResult()
    data class Success(val data:WeatherByZipResponse) : WeatherResult()
}