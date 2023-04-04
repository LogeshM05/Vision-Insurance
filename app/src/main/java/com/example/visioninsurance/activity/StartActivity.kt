package com.example.visioninsurance.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.visioninsurance.R
import io.mob.resu.reandroidsdk.AppConstants
import io.mob.resu.reandroidsdk.IDeepLinkInterface
import io.mob.resu.reandroidsdk.ReAndroidSDK
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

        AppConstants.LogFlag=true

        ReAndroidSDK.getInstance(this).getCampaignData(object : IDeepLinkInterface {
            override fun onInstallDataReceived(data: String) {
                Log.e("onInstallDataReceived", data)
            }

            override fun onDeepLinkData(Data: String) {
                Log.e("onDeepLinkData", Data)
            }
        })
    }

    private fun startMain(){

        if(!isDestroyed){
            val intent= Intent(this, MainActivity::class.java)
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