package com.deividasstr.devices.notifications

import android.app.Notification
import android.app.NotificationManager

class NotificationHandler(private val notificationManagerCompat: NotificationManager)
    : Notifications {

    override fun showNotification(notificationId: Int, notification: Notification) {
        notificationManagerCompat.notify(notificationId, notification)
    }

    override fun hideNotifications() {
        notificationManagerCompat.cancelAll()
    }
}