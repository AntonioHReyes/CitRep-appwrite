package com.tonyakitori.citrep.framework.ui.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.domain.exceptions.AccountExceptions
import com.tonyakitori.citrep.domain.exceptions.LoginExceptions
import com.tonyakitori.citrep.domain.utils.Response
import com.tonyakitori.citrep.framework.utils.createErrorLog
import com.tonyakitori.citrep.usecases.CreateAccountSessionUseCase
import io.appwrite.exceptions.AppwriteException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(private val createAccountSessionUseCase: CreateAccountSessionUseCase) :
    ViewModel() {

    private val _accountSessionLoading: MutableLiveData<Boolean> = MutableLiveData()
    val accountSessionLoading: LiveData<Boolean> get() = _accountSessionLoading

    private val _accountSession: MutableLiveData<AccountEntity?> = MutableLiveData()
    val accountSession: LiveData<AccountEntity?> get() = _accountSession

    private val _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> get() = _error

    fun createAccountSession(accountEntity: AccountEntity) {
        viewModelScope.launch {
            createAccountSessionUseCase(accountEntity).collect {
                when (it) {
                    Response.Loading -> _accountSessionLoading.postValue(true)
                    is Response.Success -> {
                        _accountSession.postValue(it.data)
                        _accountSessionLoading.postValue(false)
                    }
                    is Response.Error -> {
                        it.error.createErrorLog(ACCOUNT_SESSION_ERROR)
                        _error.postValue(handleAccountSessionErrors(it.error))
                        _accountSessionLoading.postValue(false)
                    }
                }
            }
        }
    }

    private fun handleAccountSessionErrors(error: Throwable): Exception {
        return when (error) {
            is AppwriteException -> {
                when (error.message) {
                    "Too many requests" -> AccountExceptions.TooManyRequests(error.message)
                    "Invalid credentials" -> LoginExceptions.InvalidCredentials(error.message)
                    else -> Exception()
                }
            }

            else -> Exception()
        }
    }

    companion object {
        const val ACCOUNT_SESSION_ERROR = "AccountSessionError"
    }

}