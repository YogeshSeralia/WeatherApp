package com.example.weatherapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.common.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_weather.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    val mainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    val forcastAdapter = WeeklyForcastAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUIListeners()
        showDefaultData()
    }

    private fun showDefaultData() {
        edttext_pincode.setText(Constants.defaultZip)
        fetchDataByZip()
    }
    private fun setupUIListeners() {
        btn_go.setOnClickListener {
            fetchDataByZip()
        }

        setupWeeklyForcastUI()

        mainViewModel.let { viewModel ->
            viewModel.isloading.observe(this, Observer {
                showLoading(it)
            })

            viewModel.error.observe(this, Observer {
                showError(it)
            })

            viewModel.weatherDataByZipCode.observe(this, Observer {
                cityName.text = it.name
                waether.text = it.weather[0].main
                waetherDesc.text = it.weather[0].description
                temp_celcius.text = "${it.main.temp.toCelcius()}°C"
                wind_speed.text = "Wind - ${it.wind.speed} kmh"
                humidity_percent.text = "Humidity - ${it.main.humidity} %"
                Glide.with(this).load(with(it.weather[0].icon){
                    when(this){
                        "01d","01n" -> R.drawable.clear_sky
                        "02d","02n" -> R.drawable.few_clouds
                        "03d","03n" -> R.drawable.scattered_clouds
                        "04d","04n" -> R.drawable.broken_clouds
                        "09d","09n" -> R.drawable.shower_rain
                        "10d","10n" -> R.drawable.rain
                        "11d","11n" -> R.drawable.thunderstorm
                        "13d","13n" -> R.drawable.snow
                        "50d","50n" -> R.drawable.mist
                        else -> R.drawable.clear_sky
                    }
                }).into(imgvw_weather)
            })

            viewModel.weeklyForcast.observe(this, Observer {
                forcastAdapter.setData(it.list)
            })
        }
    }

    private fun setupWeeklyForcastUI() {
        rcyclrvw_weekly_forecast.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcyclrvw_weekly_forecast.adapter = forcastAdapter
    }

    private fun fetchDataByZip() {
        val inputPinCode = edttext_pincode.text.toString()
        if (inputPinCode.length == 6) {
            mainViewModel.zipCode.value = inputPinCode.toInt()
            mainViewModel.loadWeatherByZip()
            mainViewModel.loadWeatherForWeek()
            edttext_pincode.error = null
        } else edttext_pincode.error = "Invalid Pin-Code"
    }

    private fun showError(exception: Exception) {
        layout_weather.visibility = View.GONE
        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            placeholder_weather.visibility = View.VISIBLE
            layout_weather.visibility = View.GONE
        } else {
            placeholder_weather.visibility = View.GONE
            layout_weather.visibility = View.VISIBLE
        }
    }
}

private fun Double.toCelcius(): Int {
    val d = this - 273
    return d.roundToInt()
}
