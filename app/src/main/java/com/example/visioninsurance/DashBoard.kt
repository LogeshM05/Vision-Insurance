package com.example.visioninsurance

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class DashBoard : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout = findViewById(R.id.drawerLayout)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        val lifeInsurance = findViewById<ImageView>(R.id.life)
        lifeInsurance.setOnClickListener {
            val intent = Intent(this, LifeInsurance::class.java)
            startActivity(intent)
        }
        val autoInsurance = findViewById<ImageView>(R.id.auto)
        autoInsurance.setOnClickListener {
            val intent = Intent(this, AutoInsurance::class.java)
            startActivity(intent)
        }
        val travelInsurance = findViewById<ImageView>(R.id.travel)
        travelInsurance.setOnClickListener {
            val intent = Intent(this, TravelInsurance::class.java)
            startActivity(intent)
        }
        val healthInsurance = findViewById<ImageView>(R.id.health)
        healthInsurance.setOnClickListener {
            val intent = Intent(this, HealthInsurance::class.java)
            startActivity(intent)
        }
        val accidentalInsurance = findViewById<ImageView>(R.id.accidental)
        accidentalInsurance.setOnClickListener {
            val intent = Intent(this, AccidentalInsurance::class.java)
            startActivity(intent)
        }
        val retirementPlans = findViewById<ImageView>(R.id.retire)
        retirementPlans.setOnClickListener {
            val intent = Intent(this, RetirementPlans::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
           return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id ==R.id.nav_home){
            val intent = Intent(this, DashBoard::class.java)
            startActivity(intent)
        }
        else if(id==R.id.nav_life){
            startActivity(Intent(this,LifeInsurance::class.java))
        }
        else if(id==R.id.nav_travel){
            startActivity(Intent(this,TravelInsurance::class.java))
        }
        else if(id==R.id.nav_auto){
            startActivity(Intent(this,AutoInsurance::class.java))
        }
        else if(id==R.id.nav_health){
            startActivity(Intent(this,HealthInsurance::class.java))
        }
        else if(id==R.id.nav_accidental){
            startActivity(Intent(this,AccidentalInsurance::class.java))
        }
        else if(id==R.id.nav_retirement){
            startActivity(Intent(this,RetirementPlans::class.java))
        }
        else if(id==R.id.log_out){
            startActivity(Intent(this,MainActivity::class.java))
        }
        val drawerLayout= findViewById<DrawerLayout>(R.id.drawerLayout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}



