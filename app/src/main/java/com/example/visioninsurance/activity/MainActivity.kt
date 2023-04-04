package com.example.visioninsurance.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.visioninsurance.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import io.mob.resu.reandroidsdk.IDeepLinkInterface
import io.mob.resu.reandroidsdk.ReAndroidSDK

lateinit var preference: SharedPreferences

open class MainActivity : AppCompatActivity() {

    private val emailValid =
        "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preference = getSharedPreferences("MySharedPref", MODE_PRIVATE)

        ReAndroidSDK.getInstance(this).getCampaignData(object : IDeepLinkInterface {
            override fun onInstallDataReceived(data: String) {
                Log.e("onInstallDataReceived", data)
            }

            override fun onDeepLinkData(Data: String) {
                Log.e("onDeepLinkData", Data)
            }
        })

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("onComplete", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                Log.i("token", token)
                myEdit.putString("fcmToken", token)
                updatePushToken(token)
            })

        val login = findViewById<Button>(R.id.loginButton)
        login.setOnClickListener {
            val emailField = findViewById<EditText>(R.id.username)
            val email = emailField.text.toString()
            val passField = findViewById<EditText>(R.id.password)
            val pass = passField.text.toString()

            var myEdit = preference.edit()
            myEdit.putString("email", email)

            if (email.matches(emailValid.toRegex()) && pass.isNotEmpty()) {
                val intent = Intent(this, DashBoard::class.java)
                //intent.putExtra("Name", emailValid)
                startActivity(intent)
                //  mFragmentTransaction.add(R.id.fragment_container, mFragment).commit()
            } else if (!email.matches(emailValid.toRegex())) {
                emailField.error = "Enter valid email-id"
            } else if (pass.isEmpty()) {
                passField.error = "Enter valid password"
            } else {
                Toast.makeText(this, "Invalid email or password", LENGTH_SHORT).show()
            }
        }
//        val createOne = findViewById<TextView>(R.id.bottomText_2)
//        createOne.setOnClickListener{
//            updatePushToken(myToken)
//        }

    }

    private fun updatePushToken(token: String) {
        ReAndroidSDK.getInstance(this).updatePushToken(token)
    }


}