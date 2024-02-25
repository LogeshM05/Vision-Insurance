package com.example.visioninsurance.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.os.PatternMatcher
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.visioninsurance.R
import com.example.visioninsurance.util.createNotification
import com.google.android.material.textfield.TextInputEditText
import io.mob.resu.reandroidsdk.ReAndroidSDK


class AutoInsuranceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auto_insurance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var textview1 = view.findViewById<TextView>(R.id.card_text1)
        val text = "Compare & Save upto 85%* on Car Insurance"
        val ss = SpannableString(text)
        val boldSpan = StyleSpan(Typeface.BOLD)
        ss.setSpan(boldSpan, 10, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textview1.text= ss

        val inputField = view.findViewById<TextInputEditText>(R.id.car_register)
        inputField.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
               if(s.length==4 || s.length ==7){
                   s.append('-')
               }


            }

        })

        val viewPrice = view. findViewById<Button>(R.id.submitButton)
        viewPrice.setOnClickListener {
//            showNotification()
            parentActivity.loadFragment(DashBoardFragment())
        }

    }

    private fun showNotification() {
        val title = "Welcome,"
        val body = "get more updates about Auto Insurance from Vision Insurance"
        activity?.let { createNotification(it,title,body) }

        ReAndroidSDK.getInstance(activity).addNewNotification(title,body,"com.example.visioninsurance.activity.DashBoard")
        parentActivity.loadFragment(AutoInsuranceFragment())

    }
}

