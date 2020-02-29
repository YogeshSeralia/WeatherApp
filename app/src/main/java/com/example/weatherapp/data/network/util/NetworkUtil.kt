package com.example.weatherapp.data.network.util

object NetworkUtil {
    fun concat(zipCode: Int, countryCode: String): String {
        return "$zipCode,$countryCode"
    }
}