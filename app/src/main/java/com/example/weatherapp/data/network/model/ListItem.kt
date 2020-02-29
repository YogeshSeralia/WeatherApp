package com.example.weatherapp.data.network.model

data class ListItem(
    val dt : Int,
    val main : Main,
    val weather : List<Weather>,
    val clouds : Clouds,
    val wind : Wind,
    val sys : Sys,
    val dt_txt : String
)