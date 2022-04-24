package com.tonyakitori.citrep.data.repositories

import com.tonyakitori.citrep.data.source.remote.DocumentId
import com.tonyakitori.citrep.domain.entities.PostEntity

interface PostCollectionRepository {

    suspend fun savePost(postEntity: PostEntity): DocumentId

}