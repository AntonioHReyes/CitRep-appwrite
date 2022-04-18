package com.tonyakitori.citrep.data.source.remote

import com.tonyakitori.citrep.domain.entities.AccountEntity

interface AccountDataSource {

    suspend fun createAccount(accountData: AccountEntity)
    suspend fun createAccountSession(accountData: AccountEntity)

}