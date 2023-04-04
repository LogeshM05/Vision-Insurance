package com.example.visioninsurance.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.visioninsurance.R
import com.example.visioninsurance.activity.DashBoard



lateinit var parentActivity: DashBoard

open class DashBoardFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initializeActivity()

        val view = inflater.inflate(R.layout.fragment_dash_board, container, false)

        val name = view.findViewById<TextView>(R.id.name)
//        val testName= arguments?.getString("Name")
//
//        name.text= testName

        val lifeInsurance = view.findViewById<ImageView>(R.id.life)
        lifeInsurance.setOnClickListener{
            parentActivity.loadFragment(LifeInsurance())
        }

        val autoInsurance = view.findViewById<ImageView>(R.id.auto)
        autoInsurance.setOnClickListener{
            parentActivity.loadFragment(AutoInsuranceFragment())
        }
        val healthInsurance = view.findViewById<ImageView>(R.id.health)
        healthInsurance.setOnClickListener{
            parentActivity.loadFragment(HealthInsuranceFragment())
        }
        val travelInsurance = view.findViewById<ImageView>(R.id.travel)
        travelInsurance.setOnClickListener{
            parentActivity.loadFragment(TravelInsuranceFragment())
        }
        val accidentalInsurance = view.findViewById<ImageView>(R.id.accidental)
        accidentalInsurance.setOnClickListener{
            parentActivity.loadFragment(AccidentalFragment())
        }
        val retirement = view.findViewById<ImageView>(R.id.retire)
        retirement.setOnClickListener{
            parentActivity.loadFragment(RetirementPlansFragment())
        }


        return view
}

open fun initializeActivity() {
    parentActivity = activity as DashBoard

}


}