package com.tonyakitori.citrep.usecases

import android.net.Uri
import com.tonyakitori.citrep.data.repositories.StorageRepository
import com.tonyakitori.citrep.data.source.remote.FileId
import com.tonyakitori.citrep.domain.utils.Response
import com.tonyakitori.citrep.framework.utils.createErrorLog
import com.tonyakitori.citrep.framework.utils.createInfoLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class UploadEvidences(private val storageRepository: StorageRepository) {

    suspend operator fun invoke(evidences: List<Uri>) = flow {
        emit(Response.Loading)
        val fileIdList = arrayListOf<FileId>()
        var filesWithError = 0

        evidences.forEach { fileUri ->
            storageRepository.uploadImage(fileUri).collect { response ->
                when (response) {
                    is Response.Success -> fileIdList.add(response.data)
                    is Response.Error -> {
                        response.error.createErrorLog(ERROR_IN_INDIVIDUAL_UPLOAD)
                        filesWithError++
                    }
                    else -> {
                        "...".createInfoLog(TAG)
                    }
                }
            }
        }

        emit(Response.Success(Pair(fileIdList, filesWithError)))
    }.catch { exception ->
        emit(Response.Error(exception))
    }.flowOn(Dispatchers.IO)


    companion object {
        const val TAG = "UploadEvidences"
        const val ERROR_IN_INDIVIDUAL_UPLOAD = "ErrorInImage"
    }

}