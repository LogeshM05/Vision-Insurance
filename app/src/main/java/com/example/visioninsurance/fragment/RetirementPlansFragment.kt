package com.example.visioninsurance.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.visioninsurance.R
import io.mob.resu.reandroidsdk.ReAndroidSDK

open class RetirementPlansFragment : Fragment() {

    lateinit var name: EditText
    lateinit var phone: EditText
    lateinit var email: EditText
    lateinit var radioGroup: RadioGroup
    lateinit var checkBox: CheckBox
    lateinit var submit1: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_retirement_plans, container, false)
        submit1 = view.findViewById<Button>(R.id.submit)
        submit1.setOnClickListener {
            name = view.findViewById<EditText>(R.id.editText1)
            phone = view.findViewById<EditText>(R.id.editText2)
            email = view.findViewById<EditText>(R.id.editText3)
            radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
            checkBox = view.findViewById<CheckBox>(R.id.conditions)
            formDataCapture()
            eventTracking()
            parentActivity.loadFragment(DashBoardFragment())
        }

        return view
    }

    private fun formDataCapture() {

        val formData: HashMap<String, Any> = HashMap()
        formData["formid"] = 5            // required
        formData["apikey"] = "b78db6b3-9462-4132-a4d3-894db10b3782" // required
        formData["Name"] = name.text.toString()
        formData["EmailID"] = email.text.toString()
        formData["MobileNo"] = phone.text.toString()
        formData["Gender"] = radioGroup.toString()
        ReAndroidSDK.getInstance(activity).formDataCapture(formData)

    }

    private fun eventTracking() {

        ReAndroidSDK.getInstance(activity).onTrackEvent("Registration Form")
        parentActivity.loadFragment(DashBoardFragment())
    }


}