package com.example.training

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class MyWorkCls(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        notifExpl(
            "Work Manager",
            "Testing Work Manager",
            context
        )
        //sendSms("9788577291","hi daddy")
        return Result.success()
    }

    private fun sendSms(
        phoneNumber: String,
        message: String,

        ) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
            phoneNumber, null, message, null, null
        )
    }

    private fun notifExpl(title: String, description: String, context: Context)
            : Boolean {

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel =
            NotificationChannel("101", "channel", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(notificationChannel)

        val inte = Intent(context, MainActivity::class.java)

        val pendin = PendingIntent.getActivity(
            context,
            0,
            inte,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notificationBuilder = NotificationCompat.Builder(context, "101")
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(pendin)
            .setSmallIcon(android.R.drawable.star_big_on)

        notificationManager.notify(1, notificationBuilder.build())

        return true

        //Notification:
        // Intent .
        // pendingIntent.
        // Notification.builder.
        // NotificationManager.
        // Notification Channel
        // version check

    }
}