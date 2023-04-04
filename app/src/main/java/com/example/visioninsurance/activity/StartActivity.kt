package com.example.visioninsurance.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.visioninsurance.R
import io.mob.resu.reandroidsdk.AppConstants
import io.mob.resu.reandroidsdk.IDeepLinkInterface
import io.mob.resu.reandroidsdk.ReAndroidSDK
import java.util.*
import kotlin.concurrent.timerTask

@Suppress("DEPRECATION")
class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        AppConstants.LogFlag=true

        ReAndroidSDK.getInstance(this).getCampaignData(object : IDeepLinkInterface {
            override fun onInstallDataReceived(data: String) {
                Log.e("onInstallDataReceived", data)
            }

            override fun onDeepLinkData(Data: String) {
                Log.e("onDeepLinkData", Data)
            }
        })

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val backgroundImage = findViewById<ImageView>(R.id.bg)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_slide)
        backgroundImage.startAnimation(slideAnimation)

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        //Normal Handler is deprecated , so we have to change the code little bit

        // Handler().postDelayed({
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)


       // startMain()

    }

//    private fun startMain(){
//
//        if(!isDestroyed){
//            val intent= Intent(this, MainActivity::class.java)
//            val task= timerTask {
//                if(!isDestroyed){
//
//                    startActivity(intent)
//                    finish()
//                }
//            }
//            val timer= Timer()
//            timer.schedule(task,800)
//        }
//    }

}