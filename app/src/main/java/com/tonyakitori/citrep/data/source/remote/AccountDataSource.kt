package com.tonyakitori.citrep.data.source.remote

import com.tonyakitori.citrep.domain.entities.AccountEntity
import io.appwrite.models.Token

interface AccountDataSource {

    suspend fun createAccount(accountData: AccountEntity)
    suspend fun createAccountSession(accountData: AccountEntity)
    suspend fun deleteAccountSession()
    suspend fun getAccount(): AccountEntity
    suspend fun createEmailVerification(): Token
}