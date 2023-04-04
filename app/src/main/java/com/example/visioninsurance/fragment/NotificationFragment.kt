package com.example.visioninsurance.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.visioninsurance.R
import io.mob.resu.reandroidsdk.ReAndroidSDK
import org.json.JSONObject


class NotificationFragment : Fragment() {


    lateinit var m_NotificationListView: ListView
    lateinit var m_NotificationList: ArrayList<JSONObject>
    lateinit var data :JSONObject

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initializeViews(view)

    }

    fun initializeViews(view: View) {

        var a=0

        m_NotificationList=ReAndroidSDK.getInstance(activity).notificationByObject
        for(i in m_NotificationList){
            data = m_NotificationList[a]
            a++
        }


        val title = view.findViewById<TextView>(R.id.n_title)
        val subTitle = view.findViewById<TextView>(R.id.n_subTitle)

        title.text= data.optString("title")
        subTitle.text=data.optString("subTitle")

       // m_NotificationListView = view.findViewById<ListView>(R.id.notificationListView)
        //loadData()


    }

    @SuppressLint("LongLogTag")
    private fun loadData() {
        Log.d("Inbox List1", "$")
        m_NotificationList = ReAndroidSDK.getInstance(activity).getNotificationByObject()



        // adapter = Adapter(m_NotificationList)
       // m_NotificationListView.adapter=listAdapter




        Log.d("Refreshed Notification List", "$notificationList")
    }
}


