package com.example.visioninsurance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TravelInsurance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_insurance)
        supportActionBar?.setHomeButtonEnabled(true)
        // showing the back button in action
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}