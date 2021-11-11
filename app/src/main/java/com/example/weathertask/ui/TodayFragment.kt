package com.example.weathertask.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.weathertask.R
import com.example.weathertask.TodayDataPresenter
import com.example.weathertask.TodaysWeather
import com.example.weathertask.databinding.FragmentTodayBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.core.content.ContextCompat
import android.location.Criteria
import java.util.*


class TodayFragment : Fragment() {
    lateinit var binding: FragmentTodayBinding
    lateinit var updater: Runnable
    lateinit var locationManager: LocationManager
    lateinit var provider:String
    val permsRequestCode = 1435232
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val handler = Handler(Looper.getMainLooper())
    var mPresenter = TodayDataPresenter()
    val perms = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        mPresenter.todaysWeather.observe(this, {
            binding.todaysWeather = it
        }
        )

        for (element in perms) {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    element
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(requireActivity(), perms, permsRequestCode)
                break
            }
        }

        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        provider = locationManager.getBestProvider(criteria, false).toString()


        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000,
            10.0f
        ) {
            onLocationChanged(it)
        };

        binding.tvShare.setOnClickListener {
            mPresenter.getTodaysWeather(51.5098, -0.1180)
        }

        binding.todaysWeather = TodaysWeather(
            city = "London",
            humidity = "57%",
            rainfall = "100mm",
            pressure = "1000 hPa",
            windSpeed = "24 km/h",
            tempAndWeather = "22 `C | Sunny ",
            windDegree = "SE"
        )
        return binding.root
    }

    private fun initializeUpdater() {
        val updater = Runnable {
            handler.postDelayed(updater, 1000)
        }
    }

    fun onLocationChanged(location: Location){
        mPresenter.getTodaysWeather(location.latitude, location.longitude)
    }


}