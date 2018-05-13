package com.deividasstr.devices.notifications

import android.app.Notification
import android.support.v4.app.NotificationManagerCompat

class NotificationHandler(private val notificationManagerCompat: NotificationManagerCompat)
    : Notifications {

    override fun showNotification(notificationId: Int, notification: Notification) {
        notificationManagerCompat.notify(notificationId, notification)
    }

    override fun hideNotifications() {
        notificationManagerCompat.cancelAll()
    }
}