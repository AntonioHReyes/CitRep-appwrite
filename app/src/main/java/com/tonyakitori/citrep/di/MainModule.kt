package com.tonyakitori.citrep.di

import com.tonyakitori.citrep.data.repositories.AccountRepository
import com.tonyakitori.citrep.data.repositories.AvatarRepository
import com.tonyakitori.citrep.data.repositories.PostCollectionRepository
import com.tonyakitori.citrep.data.repositories.StorageRepository
import com.tonyakitori.citrep.data.repositories.impl.AccountRepositoryImpl
import com.tonyakitori.citrep.data.repositories.impl.AvatarRepositoryImpl
import com.tonyakitori.citrep.data.repositories.impl.PostCollectionRepositoryImpl
import com.tonyakitori.citrep.data.repositories.impl.StorageRepositoryImpl
import com.tonyakitori.citrep.framework.services.FileManagementService.FilePlace.FILES
import com.tonyakitori.citrep.framework.ui.screens.login.LoginViewModel
import com.tonyakitori.citrep.framework.ui.screens.main.fragments.home.HomeViewModel
import com.tonyakitori.citrep.framework.ui.screens.main.fragments.profile.ProfileViewModel
import com.tonyakitori.citrep.framework.ui.screens.post.NewPostDialogViewModel
import com.tonyakitori.citrep.framework.ui.screens.signup.SignUpViewModel
import com.tonyakitori.citrep.usecases.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {

    //Repositories
    factory<AccountRepository> { AccountRepositoryImpl(get()) }
    factory<AvatarRepository> { AvatarRepositoryImpl(get()) }
    factory<StorageRepository> { StorageRepositoryImpl(get()) }
    factory<PostCollectionRepository> { PostCollectionRepositoryImpl(get()) }

    //useCases
    factory { CreateAccountUseCase(get()) }
    factory { CreateAccountSessionUseCase(get()) }
    factory { GetAvatarUseCase(get()) }
    factory { GetAccountUseCase(get()) }
    factory { UploadEvidences(get()) }
    factory { SavePostInDBUseCase(get(), get()) }

    //ViewModels
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { NewPostDialogViewModel(get(named(FILES)), get(), get()) }
}

val allModules = listOf(mainModule, appWriteModule, servicesModule)