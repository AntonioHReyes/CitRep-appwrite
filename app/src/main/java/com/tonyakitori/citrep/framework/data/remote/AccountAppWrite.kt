package com.tonyakitori.citrep.framework.data.remote

import com.tonyakitori.citrep.BuildConfig
import com.tonyakitori.citrep.data.source.remote.AccountDataSource
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.framework.utils.createInfoLog
import io.appwrite.Client
import io.appwrite.models.Token
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

        response.toString().createInfoLog("UserCreated")
    }

    override suspend fun createAccountSession(accountData: AccountEntity) {
        val account = Account(appWriteClient)

        val response = account.createSession(accountData.email, accountData.password)

        response.toString().createInfoLog("UserSessionCreated")
    }

    override suspend fun deleteAccountSession() {
        val account = Account(appWriteClient)
        account.deleteSession("current")
    }

    override suspend fun getAccount(): AccountEntity {
        val account = Account(appWriteClient)
        val response = account.get()

        return AccountEntity(
            userId = response.id,
            name = response.name,
            isVerified = response.emailVerification
        )
    }

    override suspend fun createEmailVerification(): Token {
        val account = Account(appWriteClient)

        return account.createVerification(url = BuildConfig.APP_VERIFICATION_URL)
    }

}