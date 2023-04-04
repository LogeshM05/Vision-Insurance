package com.example.visioninsurance.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.visioninsurance.R
import com.example.visioninsurance.activity.DashBoard
import com.example.visioninsurance.activity.myEdit
import com.example.visioninsurance.activity.preference
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.mob.resu.reandroidsdk.ReAndroidSDK

const val channelId= "notification_channel"
const val channelName="com.example.firebase"

class FirebaseReceiver : FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("token", "Refreshed token :: $token")

        myEdit.putString("fcmToken",token)
        myEdit.apply()

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("Tag1", "${remoteMessage.notification?.title}")
        Log.d("Tag1", "${remoteMessage.notification?.body}")
        Log.d("Tag1", "${remoteMessage.data}")

        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)

        }
        if(ReAndroidSDK.getInstance(this).onReceivedCampaign(remoteMessage.data))
            return

    }

    private fun showNotification(title: String, body: String) {
        val intent = Intent(this, DashBoard::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            channelId
        )
            .setSmallIcon(R.drawable.life)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, body))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
    }
    private fun getRemoteView(title: String, body: String): RemoteViews {
        val remoteView = RemoteViews(
            "com.example.visioninsurance", R.layout.notification
        )

        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, body)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.notification_icon)
        return remoteView
    }
}