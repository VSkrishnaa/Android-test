package com.Rahul.weathertrack.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.Rahul.weathertrack.R
import com.Rahul.weathertrack.databinding.ActivityMainBinding
import com.Rahul.weathertrack.ui.summary.WeeklySummaryActivity
import com.Rahul.weathertrack.ui.summary.WeatherAdapter
import com.Rahul.weathertrack.data.database.WeatherEntity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = WeatherAdapter { weatherEntity: WeatherEntity ->
            // Handle item click if needed
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshWeather()
        }

        binding.btnWeeklySummary.setOnClickListener {
            startActivity(Intent(this, WeeklySummaryActivity::class.java))
        }
    }

    private fun observeViewModel() {
        viewModel.allWeatherRecords.observe(this) { weatherList ->
            adapter.submitList(weatherList)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnRefresh.isEnabled = !isLoading
        }

        viewModel.errorMessage.observe(this) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                binding.tvStatus.text = "Error: $message"
            }
        }

        viewModel.successMessage.observe(this) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                binding.tvStatus.text = message
            }
        }
    }
}