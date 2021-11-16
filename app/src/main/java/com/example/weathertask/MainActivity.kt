package com.example.weathertask

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weathertask.databinding.ActivityMainBinding
import com.example.weathertask.ui.ForecastFragment
import com.example.weathertask.ui.TodayFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val handler = Handler(Looper.getMainLooper())
    lateinit var checker:Runnable
    var stopChecking = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checker = Runnable {
            if(!stopChecking)
                checkIfGpsEnabled()
            handler.postDelayed(checker, 1000)
        }
        handler.postDelayed(checker, 1000)


        val fragmentToday = TodayFragment()
        val fragmentForecast = ForecastFragment()

        supportFragmentManager.beginTransaction().add(R.id.main_fragment, fragmentToday).commit()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNavBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.today -> {
                    setCurrentFragment(fragmentToday)
                }
                R.id.forecast -> {
                    setCurrentFragment(fragmentForecast)
                }
            }
            false
        }


    }



    fun checkIfGpsEnabled(){
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
                finish()
            }

        val alert = builder.create();
        alert.show();
    }

    override fun onResume() {
        super.onResume()
        stopChecking = false
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit()
    }


}