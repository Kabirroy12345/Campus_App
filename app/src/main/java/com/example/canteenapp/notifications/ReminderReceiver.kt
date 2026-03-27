package com.example.canteenapp.notifications

/* 
 * ==============================================================================
 * BROADCAST RECEIVER (BACKGROUND NOTIFICATIONS)
 * ==============================================================================
 * Role in Project: This component lives in the Android OS Background. When the
 * AlarmManager (from RemindersScreen) reaches its specified time, it shouts out
 * an "Intent". This Receiver is listening for that shout.
 * 
 * Once it hears it, it wakes up the app, constructs an Android Notification Channel
 * (required for newer Android versions), and fires the push notification!
 * ==============================================================================
 */import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.canteenapp.R

/**
 * BroadcastReceiver triggered by the Android AlarmManager.
 * It builds and fires a precise notification when it's time to order.
 */
class ReminderReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "canteen_reminders"

        // Create channel for Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Order Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminds you to order meals from the canteen"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // The notification UI
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_canteen_logo)
            .setContentTitle("Time to Order!")
            .setContentText("Your favorite items might run out. Place your canteen order now.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        // Use a static ID for simplicity
        notificationManager.notify(1001, notification)
    }
}
