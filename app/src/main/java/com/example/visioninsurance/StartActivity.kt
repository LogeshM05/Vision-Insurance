package com.example.visioninsurance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import java.util.*
import kotlin.concurrent.timerTask

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        startMain()
    }
    fun startMain(){

        if(!isDestroyed){
            val intent= Intent(this,MainActivity::class.java)
            val task= timerTask {
                if(!isDestroyed){

                    startActivity(intent)
                    finish()
                }
            }
            val timer= Timer()
            timer.schedule(task,800)
        }
    }
}