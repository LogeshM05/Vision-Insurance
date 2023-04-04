package com.example.visioninsurance.activity

import android.annotation.SuppressLint
import android.content.*
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import butterknife.BindView
import butterknife.OnClick
import com.example.visioninsurance.*
import com.example.visioninsurance.R
import com.example.visioninsurance.adaptors.Adapter
import com.example.visioninsurance.fragment.*
import com.example.visioninsurance.presenter.IDashBoardActivityPresenter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import io.mob.resu.reandroidsdk.*
import org.json.JSONObject
import java.util.*


var myEdit: SharedPreferences.Editor = preference.edit()

class DashBoard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: Adapter
    lateinit var fragment: Fragment
    lateinit var iDashBoardActivityPresenter: IDashBoardActivityPresenter


    @BindView(R.id.tv_count)
    lateinit var mNotificationCount: TextView

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        // we will receive data updates in onReceive method.
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == "notification-count-update") {
                AppNotification.cancel(this@DashBoard, intent.extras!!.getInt("notificationId"))

            }
            mNotificationCount.visibility = VISIBLE
        }
    }


    @SuppressLint("MissingInflatedId", "NonConstantResourceId")

    @BindView(R.id.hamburger_menu)
    lateinit var imageView: ImageView


    @BindView(R.id.deleteButton)
    lateinit var delete: TextView


    @BindView(R.id.tv_count)
    lateinit var count: TextView

//    @BindView(R.id.logout)
//    lateinit var logoutText: TextView


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.notificationMenu)
    lateinit var notification: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)


        val back1 = findViewById<ImageView>(R.id.backMenu)
        back1.visibility = GONE

//        if (intent != null && intent.extras != null) {
//            try {
//                val bundle = intent.extras
//
//                Log.d("bundle","$bundle")
//                val activityName = bundle!!.getString("activityName", "")
//                val fragmentName = bundle.getString("fragmentName", "")
//                val jsonObject = JSONObject(bundle.getString("customParams", ""))
//               // appNavigation(fragmentName)
//            } catch (e: java.lang.Exception) {
//            }
//        }


        replaceFragment(DashBoardFragment())


        drawerLayout = findViewById(R.id.drawerLayout)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter("notification-count-update"))

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

        var notificationCount = findViewById<TextView>(R.id.tv_count)
        if (ReAndroidSDK.getInstance(this@DashBoard).unReadNotificationCount > 0) {
            notificationCount.visibility = VISIBLE
            notificationCount.text =
                "" + ReAndroidSDK.getInstance(this@DashBoard).unReadNotificationCount
        }
        resulticksRegister()

//        val bundle = intent.extras
//        if (bundle != null) {
//            if (bundle.containsKey("resumeJourney")) {
//                appNavigation(bundle.getString("resumeJourney", "{}"))
//            } else {
//                Log.e("resumeJourney", "{}")
//            }
//        }
    }

    fun appNavigation(data: String) {
        try {
            val jsonObject = JSONObject(data)
            var screenName = ""
            var fragmentName = ""
            screenName =
                if (jsonObject.optString("source", "").equals("mobile", ignoreCase = true)) {
                    jsonObject.optString("fragmentName").lowercase(Locale.getDefault())
                } else {
                    jsonObject.optString("url").lowercase(Locale.getDefault())
                }
            if (screenName.contains("accidental"))
                fragmentName = "AccidentalFragment"
            else if (screenName.contains("autoInsurance"))
                fragmentName = "AutoInsuranceFragment"
            else if (screenName.contains("dashBoard"))
                fragmentName = "DashBoardFragment"
            else if (screenName.contains("health"))
                fragmentName = "HealthInsuranceFragment"
            else if (screenName.contains("life"))
                fragmentName = "LifeInsurance"
            else if (screenName.contains("retirement"))
                fragmentName = "RetirementPlansFragment"
            else if (screenName.contains("travel"))
                fragmentName = "TravelInsuranceFragment"
            else if (screenName.contains("inbox"))
                fragmentName = "InboxFragment"
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
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
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

    @SuppressLint("CommitPrefEdits", "SetTextI18n")
    private fun resulticksRegister() {


        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("onComplete", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val token = task.result

                myEdit.putString("fcmToken", token)
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
                val uniqueId = userDetails.userUniqueId
                myEdit.putString("UUID", uniqueId)
                myEdit.apply()
                val value = preference.getString("UUID", "")
                Log.i("UUID1", "$value")


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


    @SuppressLint("SetTextI18n")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.nav_home -> {
                // drawerLayout.closeDrawer(GravityCompat.END)
                loadFragment(DashBoardFragment())

            }
            R.id.nav_life -> {
                ReAndroidSDK.getInstance(this).onTrackEvent("Apply Insurance")
                loadFragment(LifeInsurance())
            }
            R.id.nav_travel -> {
                ReAndroidSDK.getInstance(this).onLocationUpdate(13.067439, 80.237617)
                loadFragment(TravelInsuranceFragment())
            }
            R.id.nav_logout -> {
                onClickLogout()
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
        drawerLayout.closeDrawer(GravityCompat.END)
        return true
    }

    @SuppressLint("SetTextI18n")
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


    @SuppressLint("UseCompatLoadingForColorStateLists")
    @OnClick(
        R.id.conditions,
        R.id.conditions2,
        R.id.conditions3,
        R.id.conditions4,
        R.id.conditions5,
        R.id.conditions6
    )
    fun onClickCheckBox(view: View) {
        val condition1: CheckBox = findViewById<CheckBox>(R.id.conditions)
        val condition2 = findViewById<CheckBox>(R.id.conditions2)
        val condition3 = findViewById<CheckBox>(R.id.conditions3)
        val condition4 = findViewById<CheckBox>(R.id.conditions4)
        val condition5 = findViewById<CheckBox>(R.id.conditions5)
        val condition6 = findViewById<CheckBox>(R.id.conditions6)

        if (condition1.isChecked) {
            condition1.backgroundTintList =
                this.resources.getColorStateList(R.color.sky_blue)
        } else  {
            condition1.backgroundTintList =
                this.resources.getColorStateList(R.color.off_white2)
        }

        if (condition2.isChecked) {
            condition2.backgroundTintList =
                this.resources.getColorStateList(R.color.sky_blue)
        } else  {
            condition2.backgroundTintList =
                this.resources.getColorStateList(R.color.off_white2)
        }
         if (condition3.isChecked) {
            condition3.backgroundTintList =
                this.resources.getColorStateList(R.color.sky_blue)
        } else {
            condition3.backgroundTintList =
                this.resources.getColorStateList(R.color.off_white2)
        }
        if (condition4.isChecked) {
            condition4.backgroundTintList =
                this.resources.getColorStateList(R.color.sky_blue)
        } else  {
            condition4.backgroundTintList =
                this.resources.getColorStateList(R.color.off_white2)
        }
        if (condition5.isChecked) {
            condition5.backgroundTintList =
                this.resources.getColorStateList(R.color.sky_blue)
        } else  {
            condition5.backgroundTintList =
                this.resources.getColorStateList(R.color.off_white2)
        }
        if (condition6.isChecked) {
            condition6.backgroundTintList =
                this.resources.getColorStateList(R.color.sky_blue)
        } else {
            condition6.backgroundTintList =
                this.resources.getColorStateList(R.color.off_white2)
        }
    }
    @SuppressLint("UseCompatLoadingForColorStateLists")

    @OnClick(R.id.notificationMenu)
    open fun onClickNotification(v: View) {


        loadFragment(InboxFragment())
    }

    open fun onClickLogout() {

        val userDetails = MRegisterUser()
        userDetails.userUniqueId = preference.getString("UUID", "")
        val uniqueId = preference.getString("UUID", "")
        userDetails.email = preference.getString("email", "")
        userDetails.deviceToken = preference.getString("fcmToken", "")
        ReAndroidSDK.getInstance(this).onDeviceUserRegister(userDetails)

        Log.i("UUID1", "$uniqueId")
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


}



