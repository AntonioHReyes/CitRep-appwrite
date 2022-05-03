package com.tonyakitori.citrep.data.source.remote

import com.tonyakitori.citrep.domain.entities.NotificationEntity
import io.appwrite.models.RealtimeResponseEvent

interface NotificationsDataSource {

    suspend fun getNotificationsInRealTime(callback: (RealtimeResponseEvent<Any>) -> Unit)

    suspend fun getNotifications(): List<NotificationEntity>

}