package com.example.visioninsurance.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.visioninsurance.R
import io.mob.resu.reandroidsdk.ReAndroidSDK

open class RetirementPlansFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_retirement_plans, container, false)

        val submit1 = view.findViewById<Button>(R.id.submit)
        submit1.setOnClickListener{
            showNotification()
            eventTracking()
            parentActivity.loadFragment(DashBoardFragment())
        }
//
//        val checkBox = view.findViewById<CheckBox>(R.id.conditions)
//        checkBox.setOnClickListener{
//
//        }
    return view
    }

    private fun showNotification() {

        val title = "Form submitted successfully"
        val body = "Thanks for registering you will get more updates from Vision Insurance"
        ReAndroidSDK.getInstance(activity)
            .addNewNotification(title, body, "com.example.visioninsurance.activity.DashBoard")


    }

    private fun eventTracking() {

        ReAndroidSDK.getInstance(activity).onTrackEvent("Registration Form")
        parentActivity.loadFragment(DashBoardFragment())
    }


}