package com.example.visioninsurance.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import butterknife.BindView
import butterknife.OnClick
import com.example.visioninsurance.R
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

    }
}