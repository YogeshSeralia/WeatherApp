package com.example.weatherapp.data.network.model

data class WeeklyForcastResponse(
    val cod: Int,
    val message: Int,
    val cnt: Int,
    val list: List<ListItem>,
    val city: City
)