package com.example.e_museum.view_controller

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.e_museum.R
import com.example.e_museum.entities.Thing
import com.example.e_museum.view_controller.activities.ViewThingActivity


class NotificationService : Service() {
    private lateinit var thing: Thing

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thing = intent?.getSerializableExtra("thing") as Thing
        startForeground(1, createNotification())
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        thing = intent.getSerializableExtra("thing") as Thing
        return null
    }

    private fun createNotification(): Notification {
        createNotificationChannel()
        val intent = Intent(this, ViewThingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText(getString(R.string.click_here_to_open_app))
            .setSmallIcon(R.drawable.museum_icon)
            .setContentTitle(thing.name)
            .setShowWhen(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "sound",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        stopSelf()
    }
}
