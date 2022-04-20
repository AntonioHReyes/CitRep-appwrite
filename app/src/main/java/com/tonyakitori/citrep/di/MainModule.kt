package com.tonyakitori.citrep.di

import com.tonyakitori.citrep.data.repositories.AccountRepository
import com.tonyakitori.citrep.data.repositories.AvatarRepository
import com.tonyakitori.citrep.data.repositories.impl.AccountRepositoryImpl
import com.tonyakitori.citrep.data.repositories.impl.AvatarRepositoryImpl
import com.tonyakitori.citrep.framework.login.LoginViewModel
import com.tonyakitori.citrep.framework.main.fragments.profile.ProfileViewModel
import com.tonyakitori.citrep.framework.signup.SignUpViewModel
import com.tonyakitori.citrep.usecases.CreateAccountSessionUseCase
import com.tonyakitori.citrep.usecases.CreateAccountUseCase
import com.tonyakitori.citrep.usecases.GetAccountUseCase
import com.tonyakitori.citrep.usecases.GetAvatarUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    //Repositories
    factory<AccountRepository> { AccountRepositoryImpl(get()) }
    factory<AvatarRepository> { AvatarRepositoryImpl(get()) }

    //useCases
    factory { CreateAccountUseCase(get()) }
    factory { CreateAccountSessionUseCase(get()) }
    factory { GetAvatarUseCase(get()) }
    factory { GetAccountUseCase(get()) }

    //ViewModels
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
}

val allModules = listOf(mainModule, appWriteModule)