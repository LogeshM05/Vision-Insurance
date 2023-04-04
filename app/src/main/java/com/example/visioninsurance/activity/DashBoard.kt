package com.example.visioninsurance.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.OnClick
import com.example.visioninsurance.*
import com.example.visioninsurance.fragment.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import io.mob.resu.reandroidsdk.IDeepLinkInterface
import io.mob.resu.reandroidsdk.MRegisterUser
import io.mob.resu.reandroidsdk.ReAndroidSDK
import org.json.JSONObject
import java.util.*


var myEdit: SharedPreferences.Editor = preference.edit()
open class DashBoard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var fragment: Fragment

    @SuppressLint("MissingInflatedId", "NonConstantResourceId")

    @BindView(R.id.hamburger_menu)
    lateinit var imageView: ImageView

    @BindView(R.id.logout)
    lateinit var logoutText: TextView


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.notificationMenu)
    lateinit var notification: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        val back1 = findViewById<ImageView>(R.id.backMenu)
        back1.visibility = GONE

//        val message = intent.getStringExtra("Name")
//
//        val title = findViewById<TextView>(R.id.name)

//        val bundle=Bundle()
//        bundle.putString("name",message)

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayUseLogoEnabled(true)

        replaceFragment(DashBoardFragment())
        drawerLayout = findViewById(R.id.drawerLayout)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        ReAndroidSDK.getInstance(this).getCampaignData(object : IDeepLinkInterface {
            override fun onInstallDataReceived(data: String) {
                try {
                    val jsonObject = JSONObject(data)
                    loadMenu(jsonObject.optString("fragmentName"))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Log.e("onInstallDataReceived", data)
            }

            override fun onDeepLinkData(data: String) {
                Log.e("onDeepLinkData", data)
                try {
                    val jsonObject = JSONObject(data)
                    loadMenu(jsonObject.optString("fragmentName"))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
        resulticksRegister()

//        val bundle = intent.extras
//        if (bundle != null) {
//            if (bundle.containsKey("resumeJourney")) {
//              //  appNavigation(bundle.getString("resumeJourney", "{}"))
//            } else {
//                Log.e("resumeJourney", "{}")
//            }
//        }

    }

    open fun loadMenu(fragmentName: String?) {
        var fragment: Fragment? = null
        when (fragmentName) {
            "AccidentalFragment" -> fragment = AccidentalFragment()
            "AutoInsuranceFragment" -> fragment = AutoInsuranceFragment()
            "HealthInsuranceFragment" -> fragment = HealthInsuranceFragment()
            "InboxFragment" -> fragment = InboxFragment()
            "LifeInsurance" -> fragment = LifeInsurance()
            "RetirementPlansFragment" -> fragment = RetirementPlansFragment()
            "TravelInsuranceFragment" -> fragment = TravelInsuranceFragment()
        }
        fragment?.let { loadFragment(it) }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()

    }

    @SuppressLint("CommitPrefEdits")
    private fun resulticksRegister() {


        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("onComplete", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val token = task.result

                myEdit.putString("fcmToken",token)
                Log.i("token", token)

                val userDetails = MRegisterUser()
                userDetails.userUniqueId = "LG003728"
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
                val uniqueId= userDetails.userUniqueId
                myEdit.putString("UUID",uniqueId)
                myEdit.apply()
                val value= preference.getString("UUID","")
                Log.i("UUID1","$value" )


                ReAndroidSDK.getInstance(this).onDeviceUserRegister(userDetails)
            })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)

//        val drawerMenu: MenuItem = menu!!.findItem(R.id.hamburger_menu)
//        drawerMenu.setOnMenuItemClickListener {
//            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
//                drawerLayout.closeDrawer(GravityCompat.END)
//            } else {
//                drawerLayout.openDrawer(GravityCompat.END)
//            }
//            true
//        }
//
//        val menuItem: MenuItem = menu.findItem(R.id.notification)
//        menuItem.setOnMenuItemClickListener {
//            //supportFragmentManager.beginTransaction().replace(R.id.drawerLayout, InboxFragment()).commit()
//            true
//        }
//
//        val back: MenuItem = menu.findItem(R.id.back)
//        back.setOnMenuItemClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//            //supportFragmentManager.beginTransaction().replace(R.id.drawerLayout, InboxFragment()).commit()
//            true
//        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val headerName = findViewById<TextView>(R.id.header_name)
        headerName.text = intent.extras?.getString("Name") ?: "Logesh"

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                drawerLayout.closeDrawer(GravityCompat.END)

            }
            R.id.nav_life -> {
                loadFragment(LifeInsurance())
            }
            R.id.nav_travel -> {
                loadFragment(TravelInsuranceFragment())
            }
            R.id.nav_notification -> {
                loadFragment(InboxFragment())
            }
            R.id.nav_auto -> {
                loadFragment(AutoInsuranceFragment())
            }
            R.id.nav_health -> {
                loadFragment(HealthInsuranceFragment())
            }
            R.id.nav_accidental -> {
                loadFragment(AccidentalFragment())
            }
            R.id.nav_retirement -> {
                loadFragment(RetirementPlansFragment())
            }
        }
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        drawerLayout.closeDrawer(GravityCompat.END)
        return true
    }

    open fun loadFragment(fragment: Fragment) {

        val back1 = findViewById<ImageView>(R.id.backMenu)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
        if (fragment is DashBoardFragment) {
            back1.visibility = GONE
        } else {
            back1.visibility = VISIBLE
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.backMenu)
    open fun onClickBack(v: View?) {
        val back1 = findViewById<ImageView>(R.id.backMenu)
        back1.visibility = GONE
        loadFragment(DashBoardFragment())
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.hamburger_menu)
    open fun onClickMenu(v: View?) {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            drawerLayout.openDrawer(GravityCompat.END)
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.notificationMenu)
    open fun onClickNotification(v: View?) {
        loadFragment(InboxFragment())
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.logout)
    open fun onClickLogout(v: View?) {

        val userDetails = MRegisterUser()
        userDetails.userUniqueId = preference.getString("UUID","")
        val uniqueId=preference.getString("UUID","")
        userDetails.email = preference.getString("email", "")
        userDetails.deviceToken = preference.getString("fcmToken", "")
        ReAndroidSDK.getInstance(this).onDeviceUserRegister(userDetails)

        Log.i("UUID1","$uniqueId" )
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


}



