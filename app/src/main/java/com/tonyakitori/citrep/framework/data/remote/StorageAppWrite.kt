package com.tonyakitori.citrep.framework.data.remote

import android.net.Uri
import androidx.core.net.toFile
import com.tonyakitori.citrep.BuildConfig
import com.tonyakitori.citrep.data.source.remote.FileId
import com.tonyakitori.citrep.data.source.remote.StorageDataSource
import io.appwrite.Client
import io.appwrite.services.Storage

class StorageAppWrite(private val appWriteClient: Client) : StorageDataSource {

    private val storage by lazy { Storage(appWriteClient) }


    override suspend fun uploadImage(uri: Uri): FileId {

        val response = storage.createFile(
            bucketId = BuildConfig.BUCKET_COMPLAIN_ID,
            fileId = "unique()",
            file = uri.toFile()
        )

        return response.id
    }

    override suspend fun getImageForView(fileId: FileId): ByteArray {

        return storage.getFileView(
            BuildConfig.BUCKET_COMPLAIN_ID,
            fileId
        )
    }

}