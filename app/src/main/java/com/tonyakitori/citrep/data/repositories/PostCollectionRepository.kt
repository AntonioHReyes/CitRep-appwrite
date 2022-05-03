package com.tonyakitori.citrep.data.repositories

import com.tonyakitori.citrep.domain.entities.DocumentId
import com.tonyakitori.citrep.domain.entities.PostEntity
import com.tonyakitori.citrep.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface PostCollectionRepository {

    suspend fun savePost(postEntity: PostEntity): DocumentId

    suspend fun getSavedPosts(): Flow<Response<List<PostEntity>>>

}