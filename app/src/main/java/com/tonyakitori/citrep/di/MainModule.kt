package com.tonyakitori.citrep.di

import com.tonyakitori.citrep.framework.helpers.AppWriteClientManager
import org.koin.dsl.module

val mainModule = module {

    single { AppWriteClientManager(get()) }

}

val allModules = listOf(mainModule)