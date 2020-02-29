package com.example.weatherapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.common.Constants.defaultCountry
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.network.WeatherResult
import com.example.weatherapp.data.network.WeeklyForcastResult
import com.example.weatherapp.data.network.model.WeatherByZipResponse
import com.example.weatherapp.data.network.model.WeeklyForcastResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(), CoroutineScope {

    private val weatherRepository = WeatherRepository()
    val isloading: MutableLiveData<Boolean> = MutableLiveData(false)
    val error = MutableLiveData<Exception>()
    val weatherDataByZipCode: MutableLiveData<WeatherByZipResponse> = MutableLiveData()
    val weeklyForcast =  MutableLiveData<WeeklyForcastResponse>()

    val zipCode: MutableLiveData<Int> = MutableLiveData()

    private val viewModelJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob


    fun loadWeatherByZip() {
        launch {
            isloading.value = true
            val weatherByZip: WeatherResult =
                weatherRepository.getWeatherByZip(zipCode.value!!, defaultCountry)
            isloading.value = false

            when (weatherByZip) {
                is WeatherResult.Success ->{
                    weatherDataByZipCode.value = weatherByZip.data
                }
                is WeatherResult.Error ->
                    error.value = weatherByZip.exception
            }
        }
    }

    fun loadWeatherForWeek() {
        launch {
            isloading.value = true
            val weeklyForcastResult: WeeklyForcastResult =
                weatherRepository.getWeeklyWeatherForecast(zipCode.value!!, defaultCountry)
            isloading.value = false

            when (weeklyForcastResult) {
                is WeeklyForcastResult.Success ->
                    weeklyForcast.value = weeklyForcastResult.data
                is WeeklyForcastResult.Error ->
                    error.value = weeklyForcastResult.exception
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }

}