package com.example.visioninsurance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AutoInsurance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_insurance)

        supportActionBar?.setHomeButtonEnabled(true)
        // showing the back button in action
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}