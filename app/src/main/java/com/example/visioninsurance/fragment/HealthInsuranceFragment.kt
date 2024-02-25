package com.example.visioninsurance.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.example.visioninsurance.R
import com.example.visioninsurance.util.createNotification
import io.mob.resu.reandroidsdk.ReAndroidSDK

class HealthInsuranceFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health_insurance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val btn = view.findViewById<Button>(R.id.submitButton)


        btn.setOnClickListener {
            val checkBox1 = view.findViewById<CheckBox>(R.id.conditions)
            val condition2 = view.findViewById<CheckBox>(R.id.conditions2)
            val condition3 = view.findViewById<CheckBox>(R.id.conditions3)
            val condition4 = view.findViewById<CheckBox>(R.id.conditions4)
            val condition5 = view.findViewById<CheckBox>(R.id.conditions5)
            val condition6 = view.findViewById<CheckBox>(R.id.conditions6)

            if (checkBox1.isChecked || condition2.isChecked || condition3.isChecked || condition4.isChecked
                || condition5.isChecked || condition6.isChecked
            ) {
                parentActivity.loadFragment(DashBoardFragment())
            } else{
                Toast.makeText(activity, "Choose any option", Toast.LENGTH_SHORT).show()

            }
        }
    }

}