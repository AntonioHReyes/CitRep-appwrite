package com.tonyakitori.citrep.di

import com.tonyakitori.citrep.data.repositories.AccountRepository
import com.tonyakitori.citrep.data.repositories.AvatarRepository
import com.tonyakitori.citrep.data.repositories.NotificationsRepository
import com.tonyakitori.citrep.data.repositories.PostCollectionRepository
import com.tonyakitori.citrep.data.repositories.StorageRepository
import com.tonyakitori.citrep.data.repositories.impl.AccountRepositoryImpl
import com.tonyakitori.citrep.data.repositories.impl.AvatarRepositoryImpl
import com.tonyakitori.citrep.data.repositories.impl.NotificationsRepositoryImpl
import com.tonyakitori.citrep.data.repositories.impl.PostCollectionRepositoryImpl
import com.tonyakitori.citrep.data.repositories.impl.StorageRepositoryImpl
import com.tonyakitori.citrep.framework.services.FileManagementService.FilePlace.FILES
import com.tonyakitori.citrep.framework.ui.screens.login.LoginViewModel
import com.tonyakitori.citrep.framework.ui.screens.main.fragments.home.HomeViewModel
import com.tonyakitori.citrep.framework.ui.screens.main.fragments.notifications.NotificationsViewModel
import com.tonyakitori.citrep.framework.ui.screens.main.fragments.profile.ProfileViewModel
import com.tonyakitori.citrep.framework.ui.screens.post.NewPostDialogViewModel
import com.tonyakitori.citrep.framework.ui.screens.signup.SignUpViewModel
import com.tonyakitori.citrep.usecases.ConfirmEmailVerificationUseCase
import com.tonyakitori.citrep.usecases.CreateAccountSessionUseCase
import com.tonyakitori.citrep.usecases.CreateAccountUseCase
import com.tonyakitori.citrep.usecases.CreateEmailVerificationUseCase
import com.tonyakitori.citrep.usecases.GetAccountUseCase
import com.tonyakitori.citrep.usecases.GetAvatarUseCase
import com.tonyakitori.citrep.usecases.GetNotificationsInRealTimeUseCase
import com.tonyakitori.citrep.usecases.GetSavedPostsUseCase
import com.tonyakitori.citrep.usecases.LogOutUseCase
import com.tonyakitori.citrep.usecases.SavePostInDBUseCase
import com.tonyakitori.citrep.usecases.UploadEvidences
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {

    //Repositories
    factory<AccountRepository> { AccountRepositoryImpl(get()) }
    factory<AvatarRepository> { AvatarRepositoryImpl(get()) }
    factory<StorageRepository> { StorageRepositoryImpl(get()) }
    factory<PostCollectionRepository> { PostCollectionRepositoryImpl(get(), get(), get()) }
    factory<NotificationsRepository> { NotificationsRepositoryImpl(get()) }

    //useCases
    factory { CreateAccountUseCase(get()) }
    factory { CreateAccountSessionUseCase(get()) }
    factory { GetAvatarUseCase(get()) }
    factory { GetAccountUseCase(get()) }
    factory { UploadEvidences(get()) }
    factory { SavePostInDBUseCase(get(), get()) }
    factory { GetSavedPostsUseCase(get()) }
    factory { GetNotificationsInRealTimeUseCase(get()) }
    factory { CreateEmailVerificationUseCase(get()) }
    factory { LogOutUseCase(get()) }
    factory { ConfirmEmailVerificationUseCase(get()) }

    //ViewModels
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ProfileViewModel(get(), get(), get(), get(), get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { NewPostDialogViewModel(get(named(FILES)), get(), get(), get()) }
    viewModel { NotificationsViewModel(get()) }
}

val allModules = listOf(mainModule, singletonObserversModule, appWriteModule, servicesModule)