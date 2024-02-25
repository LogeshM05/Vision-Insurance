package com.example.visioninsurance.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.visioninsurance.R
import com.example.visioninsurance.fragment.listener.BackPressedListener

class AccidentalFragment : Fragment(){

//    companion object {
//        var backpressedlistener: BackPressedListener? = null
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_accidental, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btn = view. findViewById<Button>(R.id.submitButton)
        btn.setOnClickListener {
            parentActivity.loadFragment(DashBoardFragment())
        }
    }

}