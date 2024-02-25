package com.example.visioninsurance.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.visioninsurance.R
import com.example.visioninsurance.util.createNotification
import io.mob.resu.reandroidsdk.ReAndroidSDK
import java.util.*


class LifeInsurance : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_life_insurance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val btn = view. findViewById<Button>(R.id.submitButton)
        btn.setOnClickListener {

             //   showNotification()
            parentActivity.loadFragment(DashBoardFragment())

        }
    }
    private fun showNotification() {
        val title = "Welcome,"
        val body = "get more updates about Life Insurance from Vision Insurance"
        activity?.let { createNotification(it,title,body) }

        ReAndroidSDK.getInstance(activity).addNewNotification(title,body,"com.example.visioninsurance.activity.DashBoard")
        parentActivity.loadFragment(AutoInsuranceFragment())

    }

}