package com.tonyakitori.citrep.framework.helpers

import android.content.Context
import android.util.Log
import com.tonyakitori.citrep.domain.entities.AccountEntity
import io.appwrite.Client
import io.appwrite.models.User
import io.appwrite.services.Account


class AppWriteClientManager(context: Context) {

    private var client: Client = Client(context)
        .setEndpoint("http://192.168.1.72:4003/v1")
        .setProject("625b298d0c84745a378f")
        .setSelfSigned(status = true)


    suspend fun createAccount(accountData: AccountEntity): User {
        val account = Account(client)

        val response = account.create(
            userId = accountData.userId,
            email = accountData.email,
            password = accountData.password,
            name = accountData.name
        )

        Log.d("UserCreated", response.toString())

        return response
    }

    suspend fun creteAccountSession(accountData: AccountEntity) {
        val account = Account(client)

        val response = account.createSession(accountData.email, accountData.password)

        Log.d("UserSessionCreated", response.toString())
    }
}