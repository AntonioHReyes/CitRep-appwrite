package com.tonyakitori.citrep.data.source.remote

import com.tonyakitori.citrep.domain.entities.PostEntity

typealias DocumentId = String

interface PostCollectionDataSource {

    suspend fun savePost(postEntity: PostEntity): DocumentId

    suspend fun getSavedPosts(): List<PostEntity>

}