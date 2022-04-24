package com.tonyakitori.citrep.di

import com.tonyakitori.citrep.BuildConfig
import com.tonyakitori.citrep.data.source.remote.AccountDataSource
import com.tonyakitori.citrep.data.source.remote.AvatarDataSource
import com.tonyakitori.citrep.data.source.remote.PostCollectionDataSource
import com.tonyakitori.citrep.data.source.remote.StorageDataSource
import com.tonyakitori.citrep.framework.data.remote.AccountAppWrite
import com.tonyakitori.citrep.framework.data.remote.AvatarAppWrite
import com.tonyakitori.citrep.framework.data.remote.PostCollectionAppWrite
import com.tonyakitori.citrep.framework.data.remote.StorageAppWrite
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
    factory<AvatarDataSource> { AvatarAppWrite(get()) }
    factory<StorageDataSource> { StorageAppWrite(get()) }
    factory<PostCollectionDataSource> { PostCollectionAppWrite(get()) }

}