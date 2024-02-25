package com.example.visioninsurance.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
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


var userValue: SharedPreferences.Editor = preference.edit()
lateinit var mNotificationCount: TextView

class DashBoard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: Adapter
    lateinit var fragment: Fragment
    lateinit var iDashBoardActivityPresenter: IDashBoardActivityPresenter

    private var pressedTime: Long = 0


    @SuppressLint("MissingInflatedId", "NonConstantResourceId")

    @BindView(R.id.hamburger_menu)
    lateinit var imageView: ImageView


//    @BindView(R.id.deleteButton)
//    lateinit var delete: TextView



    @BindView(R.id.tv_count)
    lateinit var count: TextView


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.notificationMenu)
    lateinit var notification: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)


        val back1 = findViewById<ImageView>(R.id.backMenu)
        back1.visibility = GONE

        replaceFragment(DashBoardFragment())



        drawerLayout = findViewById(R.id.drawerLayout)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val fragmentName = intent.getStringExtra("fragmentName")
        appNavigation(fragmentName)

        val headerView = navigationView.getHeaderView(0)

        val headerItem = headerView.findViewById<TextView>(R.id.header_name)
        val textValue = preference.getString("email","")
        headerItem.text = textValue

        val lastLogin = headerView.findViewById<TextView>(R.id.textView3)
        val loginValue = preference.getString("lastLogin","")
        lastLogin.text=loginValue

        if (preference.getBoolean("isLogin", false)) {
            resulticksRegister()
        }



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

        val notificationCount = findViewById<TextView>(R.id.tv_count)
        if (ReAndroidSDK.getInstance(this@DashBoard).unReadNotificationCount > 0) {
            notificationCount.visibility = VISIBLE
            notificationCount.text =
                "" + ReAndroidSDK.getInstance(this@DashBoard).unReadNotificationCount
        }
        //resulticksRegister()


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


    private fun appNavigation(data: String?) {
        try {
            loadMenu(data)
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

//    @SuppressLint("CommitPrefEdits", "SetTextI18n")
//    private fun resulticksRegister() {
//
//
//        FirebaseMessaging.getInstance().token
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.e("onComplete", "Fetching FCM registration token failed", task.exception)
//                    return@OnCompleteListener
//                }
//                // Get new FCM registration token
//                val token = task.result
//
//                myEdit.putString("fcmToken", token)
//                Log.i("token", token)
//
//                val userDetails = MRegisterUser()
//                userDetails.userUniqueId = "LG003728"
//                userDetails.adId = "5"// mandatory
//                userDetails.name = "Logesh"
//                userDetails.email = preference.getString("email", "")
//                userDetails.phone = "+91 9600498010"
//                userDetails.age = "22"
//                userDetails.gender = "Male"
//                userDetails.profileUrl = "https://github.com/LogeshM05"
//                userDetails.dob = "05Jan2001"
//                userDetails.education = "UG"
//                userDetails.isEmployed = true
//                userDetails.isMarried = true
//                userDetails.deviceToken = token
//                val uniqueId = userDetails.userUniqueId
//                myEdit.putString("UUID", uniqueId)
//                myEdit.apply()
//                val value = preference.getString("UUID", "")
//                Log.i("UUID1", "$value")
//
//
//                ReAndroidSDK.getInstance(this).onDeviceUserRegister(userDetails)
//
//            })
//
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true
        }

        return super.onOptionsItemSelected(item)
    }


    @SuppressLint("SetTextI18n")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.nav_home -> {
                drawerLayout.closeDrawer(GravityCompat.END)
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
        var notificationCount = findViewById<TextView>(R.id.tv_count)
        if (ReAndroidSDK.getInstance(this@DashBoard).unReadNotificationCount > 0) {
            notificationCount.visibility = VISIBLE
            notificationCount.text =
                "" + ReAndroidSDK.getInstance(this@DashBoard).unReadNotificationCount
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
        } else {
            condition1.backgroundTintList =
                this.resources.getColorStateList(R.color.off_white2)
        }

        if (condition2.isChecked) {
            condition2.backgroundTintList =
                this.resources.getColorStateList(R.color.sky_blue)
        } else {
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
        } else {
            condition4.backgroundTintList =
                this.resources.getColorStateList(R.color.off_white2)
        }
        if (condition5.isChecked) {
            condition5.backgroundTintList =
                this.resources.getColorStateList(R.color.sky_blue)
        } else {
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

    fun onClickDate(v: View) {
        val date = findViewById<EditText>(R.id.editText3)
        date.setOnClickListener {

            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this, { v, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    date.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")

    @OnClick(R.id.notificationMenu, R.id.nav_notification_icon)
    open fun onClickNotification(v: View) {
        val notificationCount = findViewById<TextView>(R.id.tv_count)
        if (ReAndroidSDK.getInstance(this@DashBoard).unReadNotificationCount >= 0) {
            notificationCount.visibility = VISIBLE
            notificationCount.text =
                "" + ReAndroidSDK.getInstance(this@DashBoard).unReadNotificationCount
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        }
        loadFragment(InboxFragment())

    }

    open fun onClickLogout() {

        val userDetails = MRegisterUser()
        userDetails.userUniqueId = preference.getString("UUID", "")
        val uniqueId = preference.getString("UUID", "")
        userDetails.email = preference.getString("email", "")
        userDetails.deviceToken = preference.getString("fcmToken", "")
        ReAndroidSDK.getInstance(this).onDeviceUserRegister(userDetails)
        userValue.putBoolean("isLogin", false)
        userValue.apply()
        Log.i("UUID1", "$uniqueId")
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onBackPressed() {

        // finish()

        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)

    }


}



