package com.Rahul.weathertrack.ui.summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Rahul.weathertrack.databinding.ItemWeatherBinding
import com.Rahul.weathertrack.data.database.WeatherEntity

class WeatherAdapter(private val onClick: (WeatherEntity) -> Unit) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private var weatherList: List<WeatherEntity> = listOf()

    fun submitList(list: List<WeatherEntity>) {
        weatherList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size

    inner class WeatherViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: WeatherEntity) {
            binding.tvDate.text = weather.date
            binding.tvTemperature.text = "${weather.temperature}°C"
            binding.root.setOnClickListener { onClick(weather) }
        }
    }
}

