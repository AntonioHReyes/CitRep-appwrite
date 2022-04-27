package com.tonyakitori.citrep.di

import androidx.lifecycle.MutableLiveData
import org.koin.dsl.module

val singletonObserversModule = module {

    single { NewPostObserver() }

}

class NewPostObserver : MutableLiveData<PublishedStatus>()

enum class PublishedStatus {
    LOADING, PUBLISHED, ERROR
}