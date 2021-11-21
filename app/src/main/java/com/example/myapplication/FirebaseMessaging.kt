package com.example.myapplication

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Button
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import java.util.*

class FirebaseMessaging : FirebaseMessagingService() {

    var CHANNEL_ID = "CHANNEL"

    lateinit var title: String
    lateinit var body: String
    lateinit var icon: String

    lateinit var manager: NotificationManager

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        title = remoteMessage.notification!!.title!!
        body = remoteMessage.notification!!.body!!
        icon = remoteMessage.notification!!.icon!!

        println(title)
        println(body)
        println(icon)

        if (body == null) {
            body = Objects.requireNonNull(remoteMessage.notification!!.body)!!
        }

        manager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        sendNotification()
    }

    fun sendNotification() {

        var intent = Intent(applicationContext, NotificationActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("body", body)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID,
                "Push Notification",
                NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(notificationChannel)
        }

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.notifications_filled)
            .setAutoCancel(true)
            .setContentText(body)

        var pendingIntent =
            PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        builder.setContentIntent(pendingIntent)
        manager.notify(0, builder.build())

    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

}