package com.tonyakitori.citrep.framework.ui.screens.main.fragments.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyakitori.citrep.domain.entities.NotificationEntity
import com.tonyakitori.citrep.domain.utils.Response
import com.tonyakitori.citrep.framework.utils.createErrorLog
import com.tonyakitori.citrep.usecases.GetNotificationsInRealTimeUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val getNotificationsUseCase: GetNotificationsInRealTimeUseCase
) : ViewModel() {

    private val _notificationsLoading: MutableLiveData<Boolean> = MutableLiveData()
    val notificationsLoading: LiveData<Boolean> get() = _notificationsLoading

    private val _notifications: MutableLiveData<List<NotificationEntity>> = MutableLiveData()
    val notifications: LiveData<List<NotificationEntity>> get() = _notifications

    private val _notificationsError: MutableLiveData<Unit> = MutableLiveData()
    val notificationsError: LiveData<Unit> get() = _notificationsError

    init {
        getNotifications()
    }

    fun getNotifications() {
        viewModelScope.launch {
            getNotificationsUseCase().collect {
                when (it) {
                    Response.Loading -> _notificationsLoading.postValue(true)
                    is Response.Success -> {
                        _notifications.postValue(it.data ?: listOf())
                        _notificationsLoading.postValue(false)
                    }
                    is Response.Error -> {
                        it.error.createErrorLog(NOTIFICATIONS_ERROR)
                        _notificationsError.postValue(Unit)
                        _notificationsLoading.postValue(false)
                    }
                }
            }
        }
    }

    companion object {
        const val NOTIFICATIONS_ERROR = "GetNotificationsError"
    }
}