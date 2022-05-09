package com.tonyakitori.citrep.framework.data.remote

import com.tonyakitori.citrep.BuildConfig
import com.tonyakitori.citrep.data.source.remote.AccountDataSource
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.framework.utils.createInfoLog
import io.appwrite.Client
import io.appwrite.services.Account

class AccountAppWrite(private val appWriteClient: Client) : AccountDataSource {

    private val account by lazy { Account(appWriteClient) }

    override suspend fun createAccount(accountData: AccountEntity) {

        val response = account.create(
            userId = accountData.userId,
            email = accountData.email,
            password = accountData.password,
            name = accountData.name
        )

        response.toString().createInfoLog("UserCreated")
    }

    override suspend fun createAccountSession(accountData: AccountEntity) {

        val response = account.createSession(accountData.email, accountData.password)

        response.toString().createInfoLog("UserSessionCreated")
    }

    override suspend fun deleteAccountSession() {
        account.deleteSession("current")
    }

    override suspend fun getAccount(): AccountEntity {
        val response = account.get()

        return AccountEntity(
            userId = response.id,
            name = response.name,
            isVerified = response.emailVerification
        )
    }

    override suspend fun createEmailVerification(): String {

        return account.createVerification(url = BuildConfig.APP_VERIFICATION_URL).id
    }

    override suspend fun confirmEmailVerification(userId: String, secret: String): String {

        return account.updateVerification(userId, secret).id
    }

}