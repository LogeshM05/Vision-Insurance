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
import org.json.JSONObject
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

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        AppConstants.LogFlag=true
        preference = getSharedPreferences("UserDetails", MODE_PRIVATE)



        val bundle = intent.extras
        if (bundle != null) {
            val value = bundle.getString("customParams", "{}")
            val jsonObject = JSONObject(value)
            val fragmentValue = jsonObject.optString("fragmentName")
            Log.i("data", "$bundle")
            userValue.putString("fragmentName", fragmentValue)
            userValue.apply()
        }

        val backgroundImage = findViewById<ImageView>(R.id.bg)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_slide)
        backgroundImage.startAnimation(slideAnimation)



        if (preference.getBoolean("isLogin", false)) {
            if (bundle != null) {
                val value = bundle.getString("customParams", "{}")
                val jsonObject = JSONObject(value)
                Handler(Looper.getMainLooper()).postDelayed({
                    val fragmentValue = jsonObject.optString("fragmentName")
                    val intent = Intent(this, DashBoard::class.java)
                    intent.putExtra("fragmentName", fragmentValue)
                    startActivity(intent)
                    finish()
                }, 800)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, DashBoard::class.java)
                    startActivity(intent)
                    finish()
                }, 800)
            }
        } else {
            waitActivity(bundle)
        }

        ReAndroidSDK.getInstance(this).getCampaignData(object : IDeepLinkInterface {
            override fun onInstallDataReceived(data: String) {
                Log.e("onInstallDataReceived", data)
            }

            override fun onDeepLinkData(Data: String) {
                Log.e("onDeepLinkData", Data)
            }
        })

    }

    private fun waitActivity(bundle: Bundle?) {
        if (bundle != null) {
            val value = bundle.getString("customParams", "{}")
            val jsonObject = JSONObject(value)
            val fragmentValue = jsonObject.optString("fragmentName")
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("fragmentName", fragmentValue)
                startActivity(intent)
                finish()
            }, 800)
        } else{
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 800)
        }


    }

}