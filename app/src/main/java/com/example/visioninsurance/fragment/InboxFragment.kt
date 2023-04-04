package com.example.visioninsurance.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visioninsurance.R
import com.example.visioninsurance.activity.DashBoard
import com.example.visioninsurance.adaptors.Adapter
import com.example.visioninsurance.adaptors.NotificationAdapterListener
import io.mob.resu.reandroidsdk.AppWidgets
import io.mob.resu.reandroidsdk.ReAndroidSDK
import org.json.JSONObject
import java.util.*


lateinit var mNotificationView: RecyclerView

@SuppressLint("StaticFieldLeak")
lateinit var adapter: Adapter
var notificationList = ArrayList<JSONObject>()
var a = ArrayList<JSONObject>()

class InboxFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var holder: Adapter.ViewHolder

    var listener = object : NotificationAdapterListener {

        override fun onClickDelete(notificationDetails: ArrayList<JSONObject>, position: Int) {

            try {
                if (notificationList.size > position) {
                    ReAndroidSDK.getInstance(activity)
                        .deleteNotificationByCampaignId(notificationDetails[position].optString("campaignId"))
                    notificationList.removeAt(position)
                    if (notificationList.size == 0) {
                        adapter.resetItems(notificationList)
                        //mTvNoDataFound.setVisibility(View.VISIBLE)
                    } else {
                        adapter.notifyDataSetChanged()
                       // mTvNoDataFound.setVisibility(View.GONE)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onClick(notificationDetails: ArrayList<JSONObject>, position: Int) {
            try {
                ReAndroidSDK.getInstance(activity).readNotification(notificationDetails[position].getString("campaignId"))
                    var intent: Intent
                    try {

                        intent = Intent(activity ,DashBoard::class.java)
                        startActivity(intent)
                        activity!!.finish()

//                        intent =
//                            Intent(activity, Class.forName(notificationDetails[position].getString("navigationScreen")))
//                        intent.putExtra("activityName", notificationDetails[position].optString("activityName"))
//                        intent.putExtra("navigationScreen", notificationDetails[position].optString("activityName"))
//                        intent.putExtra("fragmentName", notificationDetails[position].optString("fragmentName"))
//                        intent.putExtra("category", notificationDetails[position].optString("fragmentName"))
//                        intent.putExtra("customParams", notificationDetails[position].optString("customParams"))
//                        intent.putExtra("MobileFriendlyUrl",notificationDetails[position].optString("customParams"))
//                        activity!!.startActivity(intent)
//                        activity!!.finish()
                    } catch (e: ClassNotFoundException) {
                        e.printStackTrace()
                    }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // initializeViews()
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_inbox, container, false)



        return view

    }

    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)

    }

    @SuppressLint("LongLogTag")
    fun getNotificationByObject(): ArrayList<JSONObject> {

        notificationList = ReAndroidSDK.getInstance(activity).getNotificationByObject();

      //  Log.d("NotificationList","$notificationList")
        if (notificationList.size == 0) {
            notificationList = ArrayList<JSONObject>()
            mNotificationView.visibility = View.GONE
        } else {
            mNotificationView.visibility = View.VISIBLE
        }
        notificationList.reverse()

        return notificationList
    }



    @SuppressLint("LongLogTag", "SuspiciousIndentation")
    fun initializeViews(view: View) {
        mNotificationView = view.findViewById<RecyclerView>(R.id.notificationView)
            loadData()
    }

    @SuppressLint("NotifyDataSetChanged", "LongLogTag")
    fun loadData() {
        Log.d("Inbox List1", "$notificationList")
        notificationList = ReAndroidSDK.getInstance(activity).getNotificationByObject()
        notificationList.reverse()
        adapter = Adapter(notificationList,listener)
        mNotificationView.layoutManager = LinearLayoutManager(activity)
        mNotificationView.adapter = adapter

        Log.d("Refreshed Notification List", "$notificationList")
    }

}




