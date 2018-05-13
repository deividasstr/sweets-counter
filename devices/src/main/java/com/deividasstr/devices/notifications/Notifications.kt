package com.deividasstr.devices.notifications

import android.app.Notification

interface Notifications {
    fun showNotification(notificationId: Int, notification: Notification)
    fun hideNotifications()
}