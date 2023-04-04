package com.example.visioninsurance.adaptors

import android.view.View
import io.mob.resu.reandroidsdk.RNotification
import org.json.JSONObject
import java.text.FieldPosition

interface NotificationAdapterListener {
    fun onClickDelete(notificationDetails: ArrayList<JSONObject>, position: Int)

    fun onClick(notificationDetails: ArrayList<JSONObject>, position: Int)
}