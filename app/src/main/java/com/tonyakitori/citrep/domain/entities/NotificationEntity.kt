package com.tonyakitori.citrep.domain.entities

data class NotificationEntity(
    val id: DocumentId,
    val title: String,
    val message: String,
    val date: Long
)