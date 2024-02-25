package com.example.visioninsurance.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visioninsurance.R
import com.example.visioninsurance.activity.DashBoard
import com.example.visioninsurance.adaptors.Adapter
import com.example.visioninsurance.adaptors.NotificationAdapterListener
import io.mob.resu.reandroidsdk.ReAndroidSDK
import io.mob.resu.reandroidsdk.ScheduleNotification
import io.mob.resu.reandroidsdk.Util
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit


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

            // ReAndroidSDK.getInstance(activity).readNotification(notificationDetails[position].getString("campaignId"))
            try {
                if (notificationList.size > position) {
                    ReAndroidSDK.getInstance(activity)
                        .deleteNotificationByCampaignId(notificationDetails[position].optString("campaignId"))
                    notificationList.removeAt(position)
                    if (notificationList.size == 0) {
                        adapter.notifyDataSetChanged()
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
                ReAndroidSDK.getInstance(activity)
                    .readNotification(notificationDetails[position].getString("campaignId"))
                var intent: Intent
                try {
                    ReAndroidSDK.getInstance(activity)
                        .deleteNotificationByCampaignId(notificationDetails[position].optString("campaignId"))
                    intent = Intent(activity, DashBoard::class.java)
                    startActivity(intent)
                    activity!!.finish()

                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        override fun onCustomAction1CLick(
            notificationDetails: ArrayList<JSONObject>,
            position: Int,
            intent: Intent
        ) {
            try {
                if (notificationList.size > position) {


                    val bundle = intent.extras

                    val customActionsString =
                        notificationDetails[position].optString("customActions")


                    // Attempt to parse the string as a JSONArray
                    val customActionsArray = JSONArray(customActionsString)
                    // Check if there is at least one action in the array
                    if (customActionsArray.length() > 0) {
                        val firstAction = customActionsArray.getJSONObject(0)
                        val actionType = firstAction.optString("actionType")

                        when (actionType) {
                            "call" -> {
                                val callIntent = Intent(Intent.ACTION_CALL)
                                callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                callIntent.data =
                                    Uri.parse("tel:" + firstAction.optString("url")) //change the number
                                if (ActivityCompat.checkSelfPermission(
                                        context!!,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    Toast.makeText(
                                        context,
                                        "Please allow Call permission",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return
                                }
                                context!!.startActivity(callIntent)
                            }

                            "maybelater" -> {
                                scheduleNotification(context!!, notificationDetails, position)
                            }

                            "dismiss" -> {
                                try {
                                    if (notificationList.size > position) {
                                        ReAndroidSDK.getInstance(activity)
                                            .deleteNotificationByCampaignId(
                                                notificationDetails[position].optString(
                                                    "campaignId"
                                                )
                                            )
                                        notificationList.removeAt(position)
                                        if (notificationList.size == 0) {
                                            adapter.notifyDataSetChanged()
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

                            "weburl" -> {
                                var url: String = firstAction.optString("url")
                                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                                    url = "http://$url"
                                }
                                val sharingIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context!!.startActivity(sharingIntent)
                            }

                            "share" -> {
                                val sendIntent = Intent(Intent.ACTION_SEND)
                                sendIntent.type = "text/plain"
                                sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                val chooseIntent = Intent.createChooser(sendIntent, "Share")
                                chooseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context!!.startActivity(chooseIntent)
                            }
                            "smartlink" -> {
                                val intent1: Intent

                                val bundle = Bundle()

                                intent1 = try {
                                    Intent(
                                        context,
                                        Class.forName(
                                            notificationDetails[position].optString("activityName").trim { it <= ' ' })
                                    )
                                } catch (e: java.lang.Exception) {
                                    Intent(
                                        context,
                                        Class.forName(Util.getLauncherActivityName(context))
                                    )
                                }
                                intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                bundle.putString(
                                    "navigationScreen",
                                    notificationDetails[position].optString("activityName")
                                )
                                bundle.putString(
                                    "customParams",
                                    notificationDetails[position].optString("customParams")
                                )
                                bundle.putString("category", notificationDetails[position].optString("fragmentName"))
                                bundle.putString(
                                    "fragmentName",
                                    notificationDetails[position].optString("fragmentName")
                                )
                                bundle.putString(
                                    "MobileFriendlyUrl",
                                    notificationDetails[position].optString("MobileFriendlyUrl")
                                )
                                intent1.putExtras(bundle)
                                context!!.startActivity(intent1)
                            }
                            "resumejourney" -> {
                                val intent1: Intent

                                val bundle = Bundle()

                                intent1 = try {
                                    Intent(
                                        context,
                                        Class.forName(
                                            notificationDetails[position].optString("activityName").trim { it <= ' ' })
                                    )
                                } catch (e: java.lang.Exception) {
                                    Intent(
                                        context,
                                        Class.forName(Util.getLauncherActivityName(context))
                                    )
                                }
                                intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                bundle.putString(
                                    "navigationScreen",
                                    notificationDetails[position].optString("activityName")
                                )
                                bundle.putString(
                                    "customParams",
                                    notificationDetails[position].optString("customParams")
                                )
                                bundle.putString("category", notificationDetails[position].optString("fragmentName"))
                                bundle.putString(
                                    "fragmentName",
                                    notificationDetails[position].optString("fragmentName")
                                )
                                bundle.putString(
                                    "MobileFriendlyUrl",
                                    notificationDetails[position].optString("MobileFriendlyUrl")
                                )
                                intent1.putExtras(bundle)
                                context!!.startActivity(intent1)
                            }
                                else -> {}
                        }
                    }
                }


            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

        }


        override fun onCustomAction2CLick(
            notificationDetails: ArrayList<JSONObject>,
            position: Int,
            intent: Intent
        ) {
            try {
                if (notificationList.size > position) {


                    val bundle = intent.extras

                    val customActionsString =
                        notificationDetails[position].optString("customActions")


                    // Attempt to parse the string as a JSONArray
                    val customActionsArray = JSONArray(customActionsString)
                    // Check if there is at least one action in the array
                    if (customActionsArray.length() > 0) {
                        val firstAction = customActionsArray.getJSONObject(1)
                        val actionType = firstAction.optString("actionType")

                        when (actionType) {
                            "call" -> {
                                val callIntent = Intent(Intent.ACTION_CALL)
                                callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                callIntent.data =
                                    Uri.parse("tel:" + firstAction.optString("url")) //change the number
                                if (ActivityCompat.checkSelfPermission(
                                        context!!,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    Toast.makeText(
                                        context,
                                        "Please allow Call permission",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return
                                }
                                context!!.startActivity(callIntent)
                            }

                            "maybelater" -> {
                                scheduleNotification(context!!, notificationDetails, position)

                            }

                            "dismiss" -> {
                                try {
                                    if (notificationList.size > position) {
                                        ReAndroidSDK.getInstance(activity)
                                            .deleteNotificationByCampaignId(
                                                notificationDetails[position].optString(
                                                    "campaignId"
                                                )
                                            )
                                        notificationList.removeAt(position)
                                        if (notificationList.size == 0) {
                                            adapter.notifyDataSetChanged()
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

                            "weburl" -> {
                                var url: String = firstAction.optString("url")
                                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                                    url = "http://$url"
                                }
                                val sharingIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context!!.startActivity(sharingIntent)
                            }

                            "share" -> {
                                val sendIntent = Intent(Intent.ACTION_SEND)
                                sendIntent.type = "text/plain"
                                sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                val chooseIntent = Intent.createChooser(sendIntent, "Share")
                                chooseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context!!.startActivity(chooseIntent)
                            }

                            "smartlink" -> {
                                val intent1: Intent

                                val bundle = Bundle()

                                intent1 = try {
                                    Intent(
                                        context,
                                        Class.forName(
                                            notificationDetails[position].optString("activityName").trim { it <= ' ' })
                                    )
                                } catch (e: java.lang.Exception) {
                                    Intent(
                                        context,
                                        Class.forName(Util.getLauncherActivityName(context))
                                    )
                                }
                                intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                bundle.putString(
                                    "navigationScreen",
                                    notificationDetails[position].optString("activityName")
                                )
                                bundle.putString(
                                    "customParams",
                                    notificationDetails[position].optString("customParams")
                                )
                                bundle.putString("category", notificationDetails[position].optString("fragmentName"))
                                bundle.putString(
                                    "fragmentName",
                                    notificationDetails[position].optString("fragmentName")
                                )
                                bundle.putString(
                                    "MobileFriendlyUrl",
                                    notificationDetails[position].optString("MobileFriendlyUrl")
                                )
                                intent1.putExtras(bundle)
                                context!!.startActivity(intent1)
                            }
                            "resumejourney" -> {
                                val intent1: Intent

                                val bundle = Bundle()

                                intent1 = try {
                                    Intent(
                                        context,
                                        Class.forName(
                                            notificationDetails[position].optString("activityName").trim { it <= ' ' })
                                    )
                                } catch (e: java.lang.Exception) {
                                    Intent(
                                        context,
                                        Class.forName(Util.getLauncherActivityName(context))
                                    )
                                }
                                intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                bundle.putString(
                                    "navigationScreen",
                                    notificationDetails[position].optString("activityName")
                                )
                                bundle.putString(
                                    "customParams",
                                    notificationDetails[position].optString("customParams")
                                )
                                bundle.putString("category", notificationDetails[position].optString("fragmentName"))
                                bundle.putString(
                                    "fragmentName",
                                    notificationDetails[position].optString("fragmentName")
                                )
                                bundle.putString(
                                    "MobileFriendlyUrl",
                                    notificationDetails[position].optString("MobileFriendlyUrl")
                                )
                                intent1.putExtras(bundle)
                                context!!.startActivity(intent1)
                            }

                            else -> {}
                        }
                    }
                }


            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

        }

        fun scheduleNotification(
            context: Context,
            notificationDetails: ArrayList<JSONObject>,
            position: Int
        ) {
            try {
                val customActionsString = notificationDetails[position].optString("customActions")
                val customActionsArray = JSONArray(customActionsString)
                if (customActionsArray.length() > 0) {
                    val firstAction = customActionsArray.getJSONObject(0)
                    val duration = firstAction.optString("duration")

                    if (!duration.isEmpty()) {
                        val bundle = Bundle()

                        bundle.putString(
                            "customParams",
                            notificationDetails[position].optString("campaignId")
                        )
                        bundle.putString("customActions", customActionsString)
                        bundle.putString("id", notificationDetails[position].optString("id"))
                        bundle.putString(
                            "isCustom",
                            notificationDetails[position].optString("isCustom")
                        )
                        bundle.putString("title", notificationDetails[position].optString("title"))
                        bundle.putString(
                            "category",
                            notificationDetails[position].optString("category")
                        )
                        bundle.putString("body", notificationDetails[position].optString("body"))
                        bundle.putString(
                            "duration",
                            notificationDetails[position].optString("duration")
                        )
                        bundle.putString(
                            "actionName",
                            notificationDetails[position].optString("actionName")
                        )
                        bundle.putString(
                            "navigationScreen",
                            notificationDetails[position].optString("navigationScreen")
                        )
                        bundle.putString(
                            "MobileFriendlyUrl",
                            notificationDetails[position].optString("MobileFriendlyUrl")
                        )
                        bundle.putInt("pushType", notificationDetails[position].optInt("pushType"))
                        bundle.putString("ttl", notificationDetails[position].optString("ttl"))
                        bundle.putString(
                            "isContent",
                            notificationDetails[position].optString("isContent")
                        )
                        bundle.putString(
                            "titleColor",
                            notificationDetails[position].optString("titleColor")
                        )
                        bundle.putString(
                            "bodyColor",
                            notificationDetails[position].optString("bodyColor")
                        )
                        bundle.putString(
                            "contentBgColor",
                            notificationDetails[position].optString("contentBgColor")
                        )
                        bundle.putString("url", notificationDetails[position].optString("url"))
                        bundle.putInt(
                            "sourceType",
                            notificationDetails[position].optInt("sourceType")
                        )

                        val notificationIntent = Intent(context, ScheduleNotification::class.java)
                        notificationIntent.putExtras(bundle)


                        val interval: String
                        var delay: Long = 0

                        when {
                            duration.contains("Minute(s)") -> {
                                interval = duration.replace("Minute(s)", "").trim()
                                delay = TimeUnit.MINUTES.toMillis(interval.toLong())
                            }

                            duration.contains("Hour(s)") -> {
                                interval = duration.replace("Hour(s)", "").trim()
                                delay = TimeUnit.HOURS.toMillis(interval.toLong())
                            }

                            duration.contains("Day(s)") -> {
                                interval = duration.replace("Day(s)", "").trim()
                                delay = TimeUnit.DAYS.toMillis(interval.toLong())
                            }
                        }

                        val pendingIntent = PendingIntent.getBroadcast(
                            context,
                            getRandom(),
                            notificationIntent,
                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                        )
                        val futureInMillis = SystemClock.elapsedRealtime() + delay

                        val alarmManager =
                            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        alarmManager.set(
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            futureInMillis,
                            pendingIntent
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Log the exception if needed
            }
        }

        fun getRandom(): Int {
            val lowerLimit = 123L
            val upperLimit = 234L
            val r = Random()
            val number = lowerLimit + (r.nextDouble() * (upperLimit - lowerLimit)).toInt()
            return number.toInt()
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

    @SuppressLint("LongLogTag", "SuspiciousIndentation")
//    fun getNotificationByObject(): ArrayList<JSONObject> {
//
//        notificationList = ReAndroidSDK.getInstance(activity).getNotificationByObject();
//
//      //  Log.d("NotificationList","$notificationList")
//        if (notificationList.size == 0) {
//            notificationList = ArrayList<JSONObject>()
//            mNotificationView.visibility = View.GONE
//        } else {
//            mNotificationView.visibility = View.VISIBLE
//        }
//        notificationList.reverse()
//
//        return notificationList
//    }


    fun initializeViews(view: View) {
        mNotificationView = view.findViewById<RecyclerView>(R.id.notificationView)
        loadData()
    }

    @SuppressLint("NotifyDataSetChanged", "LongLogTag")
    fun loadData() {
        Log.d("Inbox List1", "$notificationList")
        notificationList = ReAndroidSDK.getInstance(activity).getNotificationByObject()
        notificationList.reverse()
        adapter = Adapter(notificationList, listener)
        mNotificationView.layoutManager = LinearLayoutManager(activity)
        mNotificationView.adapter = adapter

        Log.d("Refreshed Notification List", "$notificationList")
    }

}




