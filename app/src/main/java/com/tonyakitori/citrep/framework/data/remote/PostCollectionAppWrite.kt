package com.tonyakitori.citrep.framework.data.remote

import com.tonyakitori.citrep.BuildConfig
import com.tonyakitori.citrep.data.source.remote.PostCollectionDataSource
import com.tonyakitori.citrep.domain.entities.DocumentId
import com.tonyakitori.citrep.domain.entities.PostEntity
import com.tonyakitori.citrep.framework.domain.toPostAppWrite
import io.appwrite.Client
import io.appwrite.Query
import io.appwrite.services.Database

class PostCollectionAppWrite(private val appWriteClient: Client) : PostCollectionDataSource {

    private val database by lazy { Database(appWriteClient) }

    override suspend fun savePost(postEntity: PostEntity): DocumentId {

        val response = database.createDocument(
            BuildConfig.POST_COLLECTION_ID,
            "unique()",
            data = postEntity.toPostAppWrite()
        )

        return response.id
    }

    override suspend fun getSavedPosts(): List<PostEntity> {

        val response = database.listDocuments(
            BuildConfig.POST_COLLECTION_ID,
            queries = listOf(
                Query.equal("enabled", true)
            ),
            orderAttributes = listOf(
                "date"
            ),
            orderTypes = listOf(
                "DESC"
            )
        )

        return response.convertTo { objectMapped ->
            return@convertTo PostEntity(
                id = objectMapped[ID] as DocumentId,
                userId = objectMapped[USER_ID] as String,
                userName = objectMapped[USER_NAME] as String,
                comment = objectMapped[COMMENT] as String,
                image1FileId = objectMapped[IMAGE1] as String,
                image2FileId = objectMapped[IMAGE2] as String,
                image3FileId = objectMapped[IMAGE3] as String,
                enabled = objectMapped[ENABLED] as Boolean,
                date = objectMapped[DATE] as Long,
            )
        }
    }

    companion object {
        const val ID = "\$id"
        const val USER_ID = "userId"
        const val USER_NAME = "userName"
        const val COMMENT = "comment"
        const val IMAGE1 = "image1"
        const val IMAGE2 = "image2"
        const val IMAGE3 = "image3"
        const val ENABLED = "enabled"
        const val DATE = "date"
    }


}