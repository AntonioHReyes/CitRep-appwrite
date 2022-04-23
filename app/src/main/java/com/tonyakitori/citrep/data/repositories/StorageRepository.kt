package com.tonyakitori.citrep.data.repositories

import android.net.Uri
import com.tonyakitori.citrep.data.source.remote.FileId
import com.tonyakitori.citrep.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface StorageRepository {

    suspend fun uploadImage(uri: Uri): Flow<Response<FileId>>

}