package com.tonyakitori.citrep.data.repositories.impl

import com.tonyakitori.citrep.data.repositories.PostCollectionRepository
import com.tonyakitori.citrep.data.source.remote.DocumentId
import com.tonyakitori.citrep.data.source.remote.PostCollectionDataSource
import com.tonyakitori.citrep.domain.entities.PostEntity

class PostCollectionRepositoryImpl(private val postCollectionDataSource: PostCollectionDataSource) :
    PostCollectionRepository {

    override suspend fun savePost(postEntity: PostEntity): DocumentId =
        postCollectionDataSource.savePost(postEntity)

}