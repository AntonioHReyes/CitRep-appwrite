package com.tonyakitori.citrep.usecases

import com.tonyakitori.citrep.data.repositories.NotificationsRepository

class GetNotificationsInRealTimeUseCase(private val notificationsRepository: NotificationsRepository) {

    suspend operator fun invoke() = notificationsRepository.getNotificationsInRealTime()

}