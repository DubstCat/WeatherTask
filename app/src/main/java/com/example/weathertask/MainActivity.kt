package com.example.weathertask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weathertask.databinding.ActivityMainBinding
import com.example.weathertask.ui.ForecastFragment
import com.example.weathertask.ui.TodayFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding



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