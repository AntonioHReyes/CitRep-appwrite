package com.tonyakitori.citrep.framework.data.remote

import android.util.Log
import com.tonyakitori.citrep.data.source.remote.AccountDataSource
import com.tonyakitori.citrep.domain.entities.AccountEntity
import io.appwrite.Client
import io.appwrite.services.Account

class AccountAppWrite(private val appWriteClient: Client) : AccountDataSource {

    override suspend fun createAccount(accountData: AccountEntity) {
        val account = Account(appWriteClient)

        val response = account.create(
            userId = accountData.userId,
            email = accountData.email,
            password = accountData.password,
            name = accountData.name
        )

        Log.d("UserCreated", response.toString())
    }

    override suspend fun createAccountSession(accountData: AccountEntity) {
        val account = Account(appWriteClient)

        val response = account.createSession(accountData.email, accountData.password)

        Log.d("UserSessionCreated", response.toString())
    }


}