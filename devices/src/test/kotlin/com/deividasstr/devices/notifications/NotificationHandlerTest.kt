package com.deividasstr.devices.notifications

import android.app.Notification
import android.app.NotificationManager
import com.deividasstr.devices.DeviceTestData
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class NotificationHandlerTest {

    private lateinit var notificationsImpl: NotificationHandler
    private lateinit var notificationManager: NotificationManager
    private lateinit var notification: Notification

    @Before
    @Throws(Exception::class)
    fun setUp() {
        notification = Mockito.mock(Notification::class.java)
        notificationManager = Mockito.mock(NotificationManager::class.java)
        notificationsImpl = NotificationHandler(notificationManager)
    }

    @Test
    @Throws(Exception::class)
    fun shouldShowNewNotification() {
        notificationsImpl.showNotification(DeviceTestData.TEST_INTEGER, notification)

        Mockito.verify<NotificationManager>(
                notificationManager,
                Mockito.times(1))
                .notify(DeviceTestData.TEST_INTEGER, notification)
        Mockito.verifyNoMoreInteractions(notificationManager)
    }

    @Test
    @Throws(Exception::class)
    fun shouldHideAllNotifications() {
        notificationsImpl.hideNotifications()

        Mockito.verify<NotificationManager>(
                notificationManager,
                Mockito.times(1))
                .cancelAll()
        Mockito.verifyNoMoreInteractions(notificationManager)
    }
}