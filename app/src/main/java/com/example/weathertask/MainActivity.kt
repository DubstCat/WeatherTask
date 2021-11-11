package com.example.weathertask

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.weathertask.databinding.ActivityMainBinding
import com.example.weathertask.ui.ForecastFragment
import com.example.weathertask.ui.TodayFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val fragmentToday = TodayFragment()
        val fragmentForecast = ForecastFragment()

        supportFragmentManager.beginTransaction().add(R.id.main_fragment, fragmentToday).commit()

        binding.bottomNavBar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
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

    private fun setCurrentFragment(fragment:Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit()
    }

}