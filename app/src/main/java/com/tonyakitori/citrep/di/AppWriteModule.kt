package com.tonyakitori.citrep.di

import com.tonyakitori.citrep.BuildConfig
import com.tonyakitori.citrep.data.source.remote.AccountDataSource
import com.tonyakitori.citrep.framework.data.remote.AccountAppWrite
import io.appwrite.Client
import org.koin.dsl.module

val appWriteModule = module {

    single {
        Client(get())
            .setEndpoint(BuildConfig.APPWRITE_ENDPOINT)
            .setProject(BuildConfig.PROJECT_ID)
            .setSelfSigned(status = true)
    }

    factory<AccountDataSource> { AccountAppWrite(get()) }

}