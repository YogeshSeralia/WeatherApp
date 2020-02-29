package com.example.weatherapp.data.network

import com.example.weatherapp.data.network.model.WeeklyForcastResponse

sealed class WeeklyForcastResult {
    class Error(val exception: Exception) : WeeklyForcastResult()
    data class Success(val data: WeeklyForcastResponse) : WeeklyForcastResult()
}