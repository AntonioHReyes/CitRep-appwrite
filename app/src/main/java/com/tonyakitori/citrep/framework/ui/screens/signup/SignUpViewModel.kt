package com.tonyakitori.citrep.framework.ui.screens.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.domain.utils.Response
import com.tonyakitori.citrep.framework.utils.createErrorLog
import com.tonyakitori.citrep.usecases.CreateAccountUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpViewModel(private val createAccountUseCase: CreateAccountUseCase) : ViewModel() {

    private val _accountCreationLoading: MutableLiveData<Boolean> = MutableLiveData()
    val accountCreationLoading: LiveData<Boolean> get() = _accountCreationLoading

    private val _account: MutableLiveData<AccountEntity?> = MutableLiveData()
    val account: LiveData<AccountEntity?> get() = _account

    private val _error: MutableLiveData<Unit> = MutableLiveData()
    val error: LiveData<Unit> get() = _error

    fun createAccount(accountEntity: AccountEntity) {
        viewModelScope.launch {
            createAccountUseCase(accountEntity).collect {
                when (it) {
                    Response.Loading -> _accountCreationLoading.postValue(true)
                    is Response.Success -> {
                        _account.postValue(it.data)
                        _accountCreationLoading.postValue(false)
                    }
                    is Response.Error -> {
                        it.error.createErrorLog(CREATE_ACCOUNT_ERROR)
                        _error.postValue(Unit)
                        _accountCreationLoading.postValue(false)
                    }
                }
            }
        }
    }

    companion object {
        const val CREATE_ACCOUNT_ERROR = "CreateAccountError"
    }

}