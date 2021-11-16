package com.example.weathertask.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weathertask.City
import com.example.weathertask.R
import com.example.weathertask.databinding.FragmentTodayBinding
import com.example.weathertask.presenters.TodayDataPresenter
import com.example.weathertask.utils.today.TodaysWeather
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.io.IOException
import java.util.*


class TodayFragment : Fragment() {
    lateinit var binding: FragmentTodayBinding
    private val permsRequestCode = 1435232
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val perms = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    var mPresenter = TodayDataPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        
        mPresenter.todaysWeatherObservable.subscribe(object :Observer<TodaysWeather>{
            override fun onNext(it: TodaysWeather?) {
                binding.todaysWeather = it
                binding.ivMain.setImageResource(
                    when(binding.todaysWeather?.weather){
                        "Clear" -> R.drawable.weather_sunny
                        "Rain" -> R.drawable.weather_rainy
                        "snowy" -> R.drawable.weather_snowy
                        "hail" -> R.drawable.weather_hail
                        "Wind" -> R.drawable.weather_windy
                        "Clouds" -> R.drawable.weather_cloudy
                        else -> R.drawable.weather_sunny
                    }
                )
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
            }

            override fun onComplete() {
                Log.d("TodaysObservable","Complete")
            }

            override fun onSubscribe(d: Disposable?) {
                // pass
            }

        })

        /** Заглушка потому что перестала работать геолокация*/
        //getLocation()
        mPresenter.getTodaysWeather("Minsk")
        City.name = "Minsk"
        /** Заглушка потому что перестала работать геолокация*/

        binding.tvShare.setOnClickListener {
            onShareWeather()
        }
        return binding.root
    }

    /**
     * Function for getting new data on location
     * */
    fun onLocationChanged(city: String) {
        mPresenter.getTodaysWeather(city)
    }

    fun getLocation() {
        /**
         * Request permissions for GPS
         * */
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
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location = task.result

            if (location != null) {
                val geocoder = Geocoder(requireContext(), Locale.ENGLISH)
                try {
                    val adressList =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    onLocationChanged(adressList[0].locality)
                    City.name = adressList[0].locality
                } catch (e: IOException) {
                    Toast.makeText(
                        requireActivity(),
                        "Your location is not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun onShareWeather() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Today is ${binding.todaysWeather?.tempAndWeather} in ${binding.todaysWeather?.city}"
        )
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

}