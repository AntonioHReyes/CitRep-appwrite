package com.tonyakitori.citrep.framework.ui.screens.main.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.domain.utils.Response
import com.tonyakitori.citrep.framework.utils.createErrorLog
import com.tonyakitori.citrep.usecases.GetAccountUseCase
import com.tonyakitori.citrep.usecases.GetAvatarUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getAvatarUseCase: GetAvatarUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _avatarUrl: MutableLiveData<ByteArray?> = MutableLiveData()
    val avatarUrl: LiveData<ByteArray?> get() = _avatarUrl

    private val _avatarLoading: MutableLiveData<Boolean> = MutableLiveData()
    val avatarLoading: LiveData<Boolean> get() = _avatarLoading

    private val _avatarError: MutableLiveData<Unit> = MutableLiveData()

    private val _accountData: MutableLiveData<AccountEntity?> = MutableLiveData()
    val accountData: LiveData<AccountEntity?> get() = _accountData

    private val _accountError: MutableLiveData<Unit> = MutableLiveData()

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

    private fun getAccountData() {
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


    companion object {
        const val GET_AVATAR_URL_ERROR = "GetAvatarError"
        const val GET_ACCOUNT_ERROR = "GetAccountError"
    }

}