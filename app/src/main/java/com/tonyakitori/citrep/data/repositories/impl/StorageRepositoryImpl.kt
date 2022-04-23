package com.tonyakitori.citrep.data.repositories.impl

import android.net.Uri
import com.tonyakitori.citrep.data.repositories.StorageRepository
import com.tonyakitori.citrep.data.source.remote.StorageDataSource
import com.tonyakitori.citrep.domain.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StorageRepositoryImpl(private val storageDataSource: StorageDataSource) : StorageRepository {

    override suspend fun uploadImage(uri: Uri) = flow {
        emit(Response.Loading)
        val fileId = storageDataSource.uploadImage(uri)
        emit(Response.Success(fileId))
    }.catch { exception ->
        emit(Response.Error(exception))
    }.flowOn(Dispatchers.IO)


}