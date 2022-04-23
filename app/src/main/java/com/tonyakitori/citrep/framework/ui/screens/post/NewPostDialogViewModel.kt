package com.tonyakitori.citrep.framework.ui.screens.post

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.*
import com.tonyakitori.citrep.data.source.remote.FileId
import com.tonyakitori.citrep.domain.exceptions.StorageExceptions
import com.tonyakitori.citrep.domain.utils.Response
import com.tonyakitori.citrep.framework.services.FileManagementService
import com.tonyakitori.citrep.framework.utils.createErrorLog
import com.tonyakitori.citrep.framework.utils.createInfoLog
import com.tonyakitori.citrep.usecases.UploadEvidences
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewPostDialogViewModel(
    private val fileManagementService: FileManagementService,
    private val uploadEvidences: UploadEvidences
) : ViewModel() {

    private val _evidenceList: MutableLiveData<ArrayList<Uri>> = MutableLiveData()
    val evidenceList: LiveData<ArrayList<Uri>> get() = _evidenceList

    private val _addEvidenceError: MutableLiveData<NewPostActions?> = MutableLiveData()
    val addEvidenceError: LiveData<NewPostActions?> get() = _addEvidenceError

    private val _postLoading: MutableLiveData<Boolean> = MutableLiveData()
    val postLoading: LiveData<Boolean> get() = _postLoading

    private val _uploadEvidencesError: MutableLiveData<Exception> = MutableLiveData()
    private val _postComplainError: MutableLiveData<Exception> = MutableLiveData()

    val mediatorLiveData: MediatorLiveData<Exception> = MediatorLiveData()

    init {
        mediatorLiveData.addSource(_uploadEvidencesError) {
            mediatorLiveData.postValue(it)
        }

        mediatorLiveData.addSource(_postComplainError) {
            mediatorLiveData.postValue(it)
        }
    }

    fun addEvidenceToList(bitmap: Bitmap? = null, uri: Uri? = null) {
        viewModelScope.launch {
            bitmap?.let(::handleBitmapEvidence)
            uri?.let(::handleUriEvidence)
        }
    }

    private fun handleBitmapEvidence(bitmap: Bitmap) {
        viewModelScope.launch {
            val uriOfFile = fileManagementService.saveBitmapAsFile(bitmap)
            uriOfFile?.let {
                val previousValue = _evidenceList.value ?: arrayListOf()
                previousValue.add(it)
                _evidenceList.postValue(previousValue)
            } ?: run {
                _addEvidenceError.postValue(NewPostActions.ERROR_UPLOAD_IMAGE)
            }
        }
    }

    private fun handleUriEvidence(uri: Uri) {
        val uriOfFile = fileManagementService.saveUriAsFile(uri, true)
        uriOfFile?.let {
            val previousValue = _evidenceList.value ?: arrayListOf()
            previousValue.add(it)
            _evidenceList.postValue(previousValue)
        } ?: run {
            _addEvidenceError.postValue(NewPostActions.ERROR_UPLOAD_IMAGE)
        }
    }

    fun startPostComplain() {
        viewModelScope.launch {
            val evidencesList = _evidenceList.value ?: arrayListOf()
            if (evidencesList.isNotEmpty()) {
                "Start evidences upload".createInfoLog(TAG)
                postUploadEvidences(evidencesList)
            } else {
                "Only text".createInfoLog(TAG)
                _postLoading.postValue(true)
                postComplain()
            }
        }
    }

    private suspend fun postUploadEvidences(evidencesList: ArrayList<Uri>) {
        uploadEvidences(evidencesList).collect {
            when (it) {
                Response.Loading -> _postLoading.postValue(true)
                is Response.Success -> postComplain(it.data)
                is Response.Error -> {
                    it.error.createErrorLog(ERROR_UPLOADING_EVIDENCES)
                    _uploadEvidencesError.postValue(StorageExceptions.UploadEvidencesError())
                }
            }
        }
    }

    private suspend fun postComplain(uploadEvidencesResult: Pair<ArrayList<FileId>, Int>? = null) {
        uploadEvidencesResult?.first?.createInfoLog("IdsList")
    }

    enum class NewPostActions {
        ERROR_UPLOAD_IMAGE,
    }

    companion object {
        const val TAG = "NewPostDialogViewModel"
        const val ERROR_UPLOADING_EVIDENCES = "ErrorInUpload"
    }

}