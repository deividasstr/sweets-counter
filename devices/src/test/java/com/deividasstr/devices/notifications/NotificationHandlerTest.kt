package com.deividasstr.devices.notifications

import android.app.Notification
import android.support.v4.app.NotificationManagerCompat
import com.deividasstr.devices.DeviceTestData
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class NotificationHandlerTest {

    private lateinit var notificationsImpl: NotificationHandler
    private lateinit var notificationManagerCompat: NotificationManagerCompat
    private lateinit var notification: Notification

    @Before
    @Throws(Exception::class)
    fun setUp() {
        notification = Mockito.mock(Notification::class.java)
        notificationManagerCompat = Mockito.mock(NotificationManagerCompat::class.java)
        notificationsImpl = NotificationHandler(notificationManagerCompat)
    }

    @Test
    @Throws(Exception::class)
    fun shouldShowNewNotification() {
        notificationsImpl.showNotification(DeviceTestData.TEST_INTEGER, notification)

        Mockito.verify<NotificationManagerCompat>(
                notificationManagerCompat,
                Mockito.times(1))
                .notify(DeviceTestData.TEST_INTEGER, notification)
        Mockito.verifyNoMoreInteractions(notificationManagerCompat)
    }

    @Test
    @Throws(Exception::class)
    fun shouldHideAllNotifications() {
        notificationsImpl.hideNotifications()

        Mockito.verify<NotificationManagerCompat>(
                notificationManagerCompat,
                Mockito.times(1))
                .cancelAll()
        Mockito.verifyNoMoreInteractions(notificationManagerCompat)
    }
}