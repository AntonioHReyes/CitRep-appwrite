package com.tonyakitori.citrep.data.repositories.impl

import com.tonyakitori.citrep.data.repositories.AccountRepository
import com.tonyakitori.citrep.data.source.remote.AccountDataSource
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.domain.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AccountRepositoryImpl(private val accountDataSource: AccountDataSource) : AccountRepository {

    override suspend fun createAccount(accountData: AccountEntity) = flow {
        emit(Response.Loading)
        accountDataSource.createAccount(accountData)
        emit(Response.Success(accountData))
    }.catch { error ->
        emit(Response.Error(error))
    }.flowOn(Dispatchers.IO)

    override suspend fun createAccountSession(accountData: AccountEntity) = flow {
        emit(Response.Loading)
        accountDataSource.createAccountSession(accountData)
        emit(Response.Success(accountData))
    }.catch { error ->
        emit(Response.Error(error))
    }.flowOn(Dispatchers.IO)

    override suspend fun getAccount() = flow {
        emit(Response.Loading)
        val account = accountDataSource.getAccount()
        emit(Response.Success(account))
    }.catch { error ->
        emit(Response.Error(error))
    }.flowOn(Dispatchers.IO)

}