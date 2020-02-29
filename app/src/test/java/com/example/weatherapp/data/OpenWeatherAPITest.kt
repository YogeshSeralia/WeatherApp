package com.example.weatherapp.data

import com.example.weatherapp.common.Constants
import com.example.weatherapp.data.network.model.WeatherByZipResponse
import com.example.weatherapp.data.network.OpenWeatherAPI
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class OpenWeatherAPITest {
    @Test
    fun getApiTest() = runBlocking {
        val weatherByZip: WeatherByZipResponse = OpenWeatherAPI.api.getWeatherByZip(
            Constants.defaultZipAndCountry,
            Constants.API_KEY
        )

        assertNotNull(weatherByZip)
    }
}