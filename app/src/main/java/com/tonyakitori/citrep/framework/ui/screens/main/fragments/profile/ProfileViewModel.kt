package com.tonyakitori.citrep.framework.ui.screens.main.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.domain.utils.Response
import com.tonyakitori.citrep.framework.utils.createErrorLog
import com.tonyakitori.citrep.usecases.ConfirmEmailVerificationUseCase
import com.tonyakitori.citrep.usecases.CreateEmailVerificationUseCase
import com.tonyakitori.citrep.usecases.GetAccountUseCase
import com.tonyakitori.citrep.usecases.GetAvatarUseCase
import com.tonyakitori.citrep.usecases.LogOutUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getAvatarUseCase: GetAvatarUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val createEmailVerificationUseCase: CreateEmailVerificationUseCase,
    private val confirmEmailVerificationUseCase: ConfirmEmailVerificationUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _avatarUrl: MutableLiveData<ByteArray?> = MutableLiveData()
    val avatarUrl: LiveData<ByteArray?> get() = _avatarUrl

    private val _avatarLoading: MutableLiveData<Boolean> = MutableLiveData()
    val avatarLoading: LiveData<Boolean> get() = _avatarLoading

    private val _avatarError: MutableLiveData<Unit> = MutableLiveData()

    private val _accountData: MutableLiveData<AccountEntity?> = MutableLiveData()
    val accountData: LiveData<AccountEntity?> get() = _accountData

    private val _accountError: MutableLiveData<Unit> = MutableLiveData()

    private val _emailVerification: MutableLiveData<String?> = MutableLiveData()
    val emailVerification: LiveData<String?> get() = _emailVerification

    private val _emailVerificationError: MutableLiveData<Unit> = MutableLiveData()
    val emailVerificationError: LiveData<Unit> get() = _emailVerificationError

    private val _emailVerificationLoading: MutableLiveData<Boolean> = MutableLiveData()
    val emailVerificationLoading: LiveData<Boolean> get() = _emailVerificationLoading

    private val _confirmVerificationLoading: MutableLiveData<Boolean> = MutableLiveData()
    val confirmVerificationLoading: LiveData<Boolean> get() = _confirmVerificationLoading

    private val _confirmVerificationData: MutableLiveData<String?> = MutableLiveData()
    val confirmVerificationData: LiveData<String?> get() = _confirmVerificationData

    private val _confirmVerificationError: MutableLiveData<Unit> = MutableLiveData()
    val confirmVerificationError: LiveData<Unit> get() = _confirmVerificationError

    init {
        getAvatarUrl()
        getAccountData()
    }

    private fun getAvatarUrl() {
        viewModelScope.launch {
            getAvatarUseCase().collect {
                when (it) {
                    Response.Loading -> _avatarLoading.postValue(true)
                    is Response.Success -> {
                        _avatarUrl.postValue(it.data)
                        _avatarLoading.postValue(false)
                    }
                    is Response.Error -> {
                        it.error.createErrorLog(GET_AVATAR_URL_ERROR)
                        _avatarError.postValue(Unit)
                        _avatarLoading.postValue(false)
                    }
                }
            }
        }
    }

    fun getAccountData() {
        viewModelScope.launch {
            getAccountUseCase().collect {
                when (it) {
                    is Response.Success -> {
                        _accountData.postValue(it.data)
                    }
                    is Response.Error -> {
                        it.error.createErrorLog(GET_ACCOUNT_ERROR)
                        _accountError.postValue(Unit)
                    }
                    else -> {}
                }
            }
        }
    }

    fun createEmailVerification() {
        viewModelScope.launch {
            createEmailVerificationUseCase().collect {
                when (it) {
                    Response.Loading -> _emailVerificationLoading.postValue(true)
                    is Response.Success -> {
                        _emailVerification.postValue(it.data)
                        _emailVerificationLoading.postValue(false)
                    }
                    is Response.Error -> {
                        it.error.createErrorLog(CREATE_EMAIL_VERIFICATION_ERROR)
                        _emailVerificationError.postValue(Unit)
                        _emailVerificationLoading.postValue(false)
                    }
                }
            }
        }
    }

    fun confirmEmailVerification(userId: String, secret: String) {
        viewModelScope.launch {
            confirmEmailVerificationUseCase(userId, secret).collect {
                when (it) {
                    Response.Loading -> _confirmVerificationLoading.postValue(true)
                    is Response.Success -> {
                        delay(3_500)
                        _confirmVerificationData.postValue(it.data)
                        _confirmVerificationLoading.postValue(false)
                    }
                    is Response.Error -> {
                        it.error.createErrorLog(CONFIRM_EMAIL_VERIFICATION_ERROR)
                        _confirmVerificationError.postValue(Unit)
                        _confirmVerificationLoading.postValue(false)
                    }
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
        }
    }

    companion object {
        const val GET_AVATAR_URL_ERROR = "GetAvatarError"
        const val GET_ACCOUNT_ERROR = "GetAccountError"
        const val CREATE_EMAIL_VERIFICATION_ERROR = "CreateEmailVerificationErr"
        const val CONFIRM_EMAIL_VERIFICATION_ERROR = "ConfEmailVerificationErr"
    }

}