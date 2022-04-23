package com.tonyakitori.citrep.framework.ui.screens.main.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyakitori.citrep.domain.utils.Response
import com.tonyakitori.citrep.usecases.GetAvatarUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val getAvatarUseCase: GetAvatarUseCase) : ViewModel() {

    private val _avatar: MutableLiveData<ByteArray?> = MutableLiveData()
    val avatar: LiveData<ByteArray?> get() = _avatar

    init {
        getAvatarInitials()
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

}