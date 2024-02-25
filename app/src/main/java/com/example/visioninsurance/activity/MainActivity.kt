package com.example.visioninsurance.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.visioninsurance.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import io.mob.resu.reandroidsdk.IDeepLinkInterface
import io.mob.resu.reandroidsdk.MRegisterUser
import io.mob.resu.reandroidsdk.ReAndroidSDK
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

lateinit var preference: SharedPreferences

open class MainActivity : AppCompatActivity() {

    private val emailValid =
        "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentName = intent.getStringExtra("fragmentName")

        preference = getSharedPreferences("UserDetails", MODE_PRIVATE)


        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("onComplete", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                userValue.putString("fcmToken", token)
                updatePushToken(token)
            })


        ReAndroidSDK.getInstance(this).getCampaignData(object : IDeepLinkInterface {
            override fun onInstallDataReceived(data: String) {
                Log.e("onInstallDataReceived", data)
            }

            override fun onDeepLinkData(Data: String) {
                Log.e("onDeepLinkData", Data)
            }
        })


        val login = findViewById<Button>(R.id.loginButton)
        login.setOnClickListener {
            val emailField = findViewById<EditText>(R.id.emailText)
            val email = emailField.text.toString()
            val passField = findViewById<EditText>(R.id.password)
            val pass = passField.text.toString()

            if (email.matches(emailValid.toRegex()) && pass.isNotEmpty()) {
                if (fragmentName != null) {
                    userValue.putString("email", email)
                    userValue.putBoolean("isLogin", true)
                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    userValue.putString("lastLogin", currentDate)
                    userValue.apply()
                    val intent = Intent(this, DashBoard::class.java)
                    intent.putExtra("fragmentName", fragmentName)
                    startActivity(intent)
                    resulticksRegister()
                } else {
                    userValue.putString("email", email)
                    userValue.putBoolean("isLogin", true)
                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    userValue.putString("lastLogin", currentDate)
                    userValue.apply()
                    val intent = Intent(this, DashBoard::class.java)
                    startActivity(intent)
                    resulticksRegister()
                }
            } else if (!email.matches(emailValid.toRegex())) {
                emailField.error = "Enter valid email-id"
            } else if (pass.isEmpty()) {
                passField.error = "Enter valid password"
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun resulticksRegister() {

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("onComplete", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                val userDetails = MRegisterUser()
                userDetails.userUniqueId = preference.getString("email", "")
                userDetails.adId = "5"// mandatory
                userDetails.name = "Logesh"
                userDetails.email = preference.getString("email", "")
                userDetails.phone = "+91 9600498010"
                userDetails.age = "22"
                userDetails.gender = "Male"
                userDetails.profileUrl = "https://github.com/LogeshM05"
                userDetails.dob = "05Jan2001"
                userDetails.education = "UG"
                userDetails.isEmployed = true
                userDetails.isMarried = true
                userDetails.deviceToken = token
                val uniqueId = userDetails.userUniqueId
                userValue.putString("UUID", uniqueId)
                userValue.apply()
                val value = preference.getString("UUID", "")
                Log.i("UUID1", "$value")

                ReAndroidSDK.getInstance(this).onDeviceUserRegister(userDetails)

            })

    }

    private fun location() {
        ReAndroidSDK.getInstance(this).onLocationUpdate(13.42, 84.22)
    }


    private fun updatePushToken(token: String) {
        ReAndroidSDK.getInstance(this).updatePushToken(token)
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

}