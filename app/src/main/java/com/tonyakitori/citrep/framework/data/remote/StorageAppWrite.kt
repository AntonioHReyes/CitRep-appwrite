package com.tonyakitori.citrep.framework.data.remote

import android.net.Uri
import androidx.core.net.toFile
import com.tonyakitori.citrep.BuildConfig
import com.tonyakitori.citrep.data.source.remote.FileId
import com.tonyakitori.citrep.data.source.remote.StorageDataSource
import io.appwrite.Client
import io.appwrite.services.Storage

class StorageAppWrite(private val appWriteClient: Client) : StorageDataSource {

    override suspend fun uploadImage(uri: Uri): FileId {
        val storage = Storage(appWriteClient)

        val response = storage.createFile(
            bucketId = BuildConfig.BUCKET_COMPLAIN_ID,
            fileId = "unique()",
            file = uri.toFile()
        )

        return response.id
    }

}