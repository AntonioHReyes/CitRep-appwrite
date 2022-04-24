package com.tonyakitori.citrep.framework.data.remote

import com.tonyakitori.citrep.BuildConfig
import com.tonyakitori.citrep.data.source.remote.DocumentId
import com.tonyakitori.citrep.data.source.remote.PostCollectionDataSource
import com.tonyakitori.citrep.domain.entities.PostEntity
import com.tonyakitori.citrep.framework.domain.toPostAppWrite
import io.appwrite.Client
import io.appwrite.services.Database

class PostCollectionAppWrite(private val appWriteClient: Client) : PostCollectionDataSource {

    override suspend fun savePost(postEntity: PostEntity): DocumentId {
        val database = Database(appWriteClient)

        val response = database.createDocument(
            BuildConfig.POST_COLLECTION_ID,
            "unique()",
            data = postEntity.toPostAppWrite()
        )

        return response.id
    }


}