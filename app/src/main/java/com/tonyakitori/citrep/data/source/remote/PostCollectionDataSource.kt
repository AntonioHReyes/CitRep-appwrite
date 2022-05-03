package com.tonyakitori.citrep.data.source.remote

import com.tonyakitori.citrep.domain.entities.DocumentId
import com.tonyakitori.citrep.domain.entities.PostEntity

interface PostCollectionDataSource {

    suspend fun savePost(postEntity: PostEntity): DocumentId

    suspend fun getSavedPosts(): List<PostEntity>

}