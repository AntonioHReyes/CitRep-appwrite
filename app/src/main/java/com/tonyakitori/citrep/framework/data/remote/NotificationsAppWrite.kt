package com.tonyakitori.citrep.framework.data.remote

import com.tonyakitori.citrep.BuildConfig
import com.tonyakitori.citrep.data.source.remote.NotificationsDataSource
import com.tonyakitori.citrep.domain.entities.DocumentId
import com.tonyakitori.citrep.domain.entities.NotificationEntity
import io.appwrite.Client
import io.appwrite.models.RealtimeResponseEvent
import io.appwrite.services.Database
import io.appwrite.services.Realtime

class NotificationsAppWrite(private val appWriteClient: Client) : NotificationsDataSource {

    private val database by lazy { Database(appWriteClient) }
    private val realtime: Realtime by lazy { Realtime(appWriteClient) }

    override suspend fun getNotificationsInRealTime(callback: (RealtimeResponseEvent<Any>) -> Unit) {
        realtime.subscribe(
            "collections.${BuildConfig.NOTIFICATIONS_COLLECTION_ID}.documents",
            callback = callback
        )
    }

    override suspend fun getNotifications(): List<NotificationEntity> {

        val response = database.listDocuments(BuildConfig.NOTIFICATIONS_COLLECTION_ID)

        return response.convertTo { objectMapped ->
            NotificationEntity(
                id = objectMapped[ID] as DocumentId,
                title = objectMapped[TITLE] as String,
                message = objectMapped[MESSAGE] as String,
                date = objectMapped[DATE] as Long,
            )
        }
    }

    companion object {
        const val ID = "\$id"
        const val TITLE = "title"
        const val MESSAGE = "message"
        const val DATE = "date"
    }

}