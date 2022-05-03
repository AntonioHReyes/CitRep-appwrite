package com.tonyakitori.citrep.data.repositories.impl

import com.tonyakitori.citrep.data.repositories.NotificationsRepository
import com.tonyakitori.citrep.data.source.remote.NotificationsDataSource
import com.tonyakitori.citrep.domain.entities.NotificationEntity
import com.tonyakitori.citrep.domain.utils.Response
import com.tonyakitori.citrep.framework.utils.createInfoLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotificationsRepositoryImpl(private val notificationsDataSource: NotificationsDataSource) :
    NotificationsRepository {

    override suspend fun getNotificationsInRealTime(): Flow<Response<List<NotificationEntity>>> =
        callbackFlow {
            trySendBlocking(Response.Loading)

            val response = notificationsDataSource.getNotifications()
            trySendBlocking(Response.Success(response))

            notificationsDataSource.getNotificationsInRealTime {
                CoroutineScope(Dispatchers.IO).launch {
                    val callbackResponse = notificationsDataSource.getNotifications()
                    trySendBlocking(Response.Success(callbackResponse))
                }
            }

            awaitClose {
                cancel()
            }
        }.catch { exception ->
            "Que recibimos $exception".createInfoLog("RealTimeNotifications")
            emit(Response.Error(exception))
        }.flowOn(Dispatchers.IO)


}