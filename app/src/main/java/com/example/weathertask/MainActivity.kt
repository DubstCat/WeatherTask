package com.example.weathertask

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weathertask.databinding.ActivityMainBinding
import com.example.weathertask.ui.ForecastFragment
import com.example.weathertask.ui.TodayFragment
import com.example.weathertask.utils.CityObservable
import com.example.weathertask.utils.ConnectionDetector
import com.google.android.gms.location.*
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.io.IOException
import java.util.*
import android.content.SharedPreferences
import android.content.res.Configuration


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var checker: Runnable
    val handler = Handler(Looper.getMainLooper())
    val fragmentToday = TodayFragment()
    val fragmentForecast = ForecastFragment()
    val permsRequestCode = 12413423
    val SAVE = "SAVE"
    var stopChecking = false
    private val perms = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.title = "Weather"

        if (!isConnectedToInternet) {
            Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show()
        }

        supportFragmentManager.beginTransaction().add(R.id.main_fragment, fragmentForecast)
            .hide(fragmentForecast)
            .add(R.id.main_fragment, fragmentToday).show(fragmentToday).commit()

        val loadedCity = loadCity()

        if (loadedCity != "" && isConnectedToInternet) {
            CityObservable.name.onNext(loadCity())
        }

        CityObservable.name.subscribe(getLoadingObserver())

        checker = Runnable {
            if (!stopChecking)
                checkIfGpsEnabled()
            handler.postDelayed(checker, 1000)
        }
        handler.postDelayed(checker, 1000)

        getLocation()
        setupNavBar()
    }

    private fun setupNavBar() {
        binding.bottomNavBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.today -> {
                    setCurrentFragment(fragmentForecast, fragmentToday)
                }
                R.id.forecast -> {
                    setCurrentFragment(fragmentToday, fragmentForecast)
                }
            }
            false
        }
    }

    val isConnectedToInternet: Boolean
        get() = ConnectionDetector(applicationContext).isConnectingToInternet

    fun checkIfGpsEnabled() {
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager;

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            stopChecking = true
        }
    }

    fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
                android.os.Process.killProcess(android.os.Process.myPid())
            }

        val alert = builder.create()
        alert.show()
    }

    fun getLocation() {

        /**
         * Request permissions for GPS
         * */
        for (element in perms) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    element
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, perms, permsRequestCode)
                break
            }
        }

        val mLocationRequest: LocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {
                        val geocoder = Geocoder(this@MainActivity, Locale.ENGLISH)
                        try {
                            val adressList =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            if (isConnectedToInternet)
                                CityObservable.name.onNext(adressList[0].locality)
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Toast.makeText(
                                this@MainActivity,
                                "Your location is not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper())

    }

    override fun onResume() {
        super.onResume()
        stopChecking = false
    }

    private fun setCurrentFragment(from: Fragment, to: Fragment) {
        supportFragmentManager.beginTransaction().hide(from).show(to).commit()
    }

    private fun getLoadingObserver(): Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable?) {
            // pass
        }

        override fun onNext(t: String?) {
            if (t != null) {
                saveCity(t)
            }
            binding.loagingBar.visibility = View.GONE

        }

        override fun onError(e: Throwable?) {
            e?.printStackTrace()
        }

        override fun onComplete() {
            // pass
        }
    }


    //Сохранение
    fun saveCity(city: String) {
        val sPref = getSharedPreferences("Save", MODE_MULTI_PROCESS)
        val ed: SharedPreferences.Editor = sPref.edit()
        ed.putString(SAVE, city).apply()
    }

    //Загрузка
    fun loadCity(): String? {
        val sPref = getSharedPreferences("Save", MODE_MULTI_PROCESS)
        return sPref.getString(SAVE, "")
    }


}