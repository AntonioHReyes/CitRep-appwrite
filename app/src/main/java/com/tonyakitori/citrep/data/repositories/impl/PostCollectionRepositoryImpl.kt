package com.tonyakitori.citrep.data.repositories.impl

import com.tonyakitori.citrep.data.repositories.PostCollectionRepository
import com.tonyakitori.citrep.data.source.remote.AvatarDataSource
import com.tonyakitori.citrep.data.source.remote.PostCollectionDataSource
import com.tonyakitori.citrep.data.source.remote.StorageDataSource
import com.tonyakitori.citrep.domain.entities.DocumentId
import com.tonyakitori.citrep.domain.entities.PostEntity
import com.tonyakitori.citrep.domain.entities.UserPostEntity
import com.tonyakitori.citrep.domain.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostCollectionRepositoryImpl(
    private val postCollectionDataSource: PostCollectionDataSource,
    private val avatarDataSource: AvatarDataSource,
    private val storageDataSource: StorageDataSource
) : PostCollectionRepository {

    override suspend fun savePost(postEntity: PostEntity): DocumentId =
        postCollectionDataSource.savePost(postEntity)

    override suspend fun getSavedPosts() = flow {
        emit(Response.Loading)

        val postsSavedList = postCollectionDataSource.getSavedPosts()
            .map { addUserInfoToPost(it) }
            .map { addImageFiles(it) }

        emit(Response.Success(postsSavedList))
    }.catch { exception ->
        emit(Response.Error(exception))
    }.flowOn(Dispatchers.IO)

    private suspend fun addUserInfoToPost(postEntity: PostEntity): PostEntity {

        val name = postEntity.userName

        postEntity.user = UserPostEntity(
            name = name,
            avatar = avatarDataSource.getAvatarName(name)
        )

        return postEntity
    }

    private suspend fun addImageFiles(postEntity: PostEntity): PostEntity {

        if (postEntity.image1FileId.isNotBlank()) {
            postEntity.image1ByteArray = storageDataSource.getImageForView(postEntity.image1FileId)
        }

        if (postEntity.image2FileId.isNotBlank()) {
            postEntity.image2ByteArray = storageDataSource.getImageForView(postEntity.image2FileId)
        }

        if (postEntity.image3FileId.isNotBlank()) {
            postEntity.image3ByteArray = storageDataSource.getImageForView(postEntity.image3FileId)
        }

        return postEntity
    }

}