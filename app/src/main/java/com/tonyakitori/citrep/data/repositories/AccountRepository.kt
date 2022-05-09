package com.tonyakitori.citrep.data.repositories

import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun createAccount(accountData: AccountEntity): Flow<Response<AccountEntity>>
    suspend fun createAccountSession(accountData: AccountEntity): Flow<Response<AccountEntity>>
    suspend fun deleteAccountSession(): Flow<Response<Boolean>>
    suspend fun getAccount(): Flow<Response<AccountEntity>>
    suspend fun createEmailVerification(): Flow<Response<String>>
    suspend fun confirmEmailVerification(userId: String, secret: String): Flow<Response<String>>

}