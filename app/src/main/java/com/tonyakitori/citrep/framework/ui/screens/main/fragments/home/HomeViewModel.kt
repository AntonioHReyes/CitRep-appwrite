package com.tonyakitori.citrep.framework.ui.screens.main.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyakitori.citrep.di.NewPostObserver
import com.tonyakitori.citrep.domain.entities.PostEntity
import com.tonyakitori.citrep.domain.utils.Response
import com.tonyakitori.citrep.framework.utils.createErrorLog
import com.tonyakitori.citrep.usecases.GetAvatarUseCase
import com.tonyakitori.citrep.usecases.GetSavedPostsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAvatarUseCase: GetAvatarUseCase,
    private val getSavedPostsUseCase: GetSavedPostsUseCase,
    val newPostObserver: NewPostObserver
) : ViewModel() {

    private val _avatar: MutableLiveData<ByteArray?> = MutableLiveData()
    val avatar: LiveData<ByteArray?> get() = _avatar

    private val _posts: MutableLiveData<List<PostEntity>> = MutableLiveData()
    val posts: LiveData<List<PostEntity>> get() = _posts

    private val _postsLoading: MutableLiveData<Boolean> = MutableLiveData()
    val postsLoading: LiveData<Boolean> get() = _postsLoading

    private val _postsError: MutableLiveData<Unit> = MutableLiveData()
    val postsError: LiveData<Unit> get() = _postsError

    init {
        getAvatarInitials()
        getSavedPosts()
    }

    private fun getAvatarInitials() {
        viewModelScope.launch {
            getAvatarUseCase().collect {
                when (it) {
                    is Response.Success -> {
                        _avatar.postValue(it.data)
                    }
                    else -> {}
                }
            }
        }
    }

    fun getSavedPosts() {
        viewModelScope.launch {
            getSavedPostsUseCase().collect {
                when (it) {
                    is Response.Success -> {
                        _posts.postValue(it.data ?: listOf())
                        _postsLoading.postValue(false)
                    }
                    is Response.Error -> {
                        it.error.createErrorLog(ERROR_IN_GET_POSTS)
                        _postsError.postValue(Unit)
                        _posts.postValue(listOf())
                        _postsLoading.postValue(false)
                    }
                    Response.Loading -> _postsLoading.postValue(true)
                }
            }
        }
    }


    companion object {
        const val ERROR_IN_GET_POSTS = ""
    }

}