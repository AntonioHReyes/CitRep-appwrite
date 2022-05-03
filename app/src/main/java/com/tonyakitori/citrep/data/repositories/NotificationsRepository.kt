package com.tonyakitori.citrep.data.repositories

import com.tonyakitori.citrep.domain.entities.NotificationEntity
import com.tonyakitori.citrep.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface NotificationsRepository {

    suspend fun getNotificationsInRealTime(): Flow<Response<List<NotificationEntity>>>

}