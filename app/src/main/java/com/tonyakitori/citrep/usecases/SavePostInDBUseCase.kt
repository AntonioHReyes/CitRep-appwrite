package com.tonyakitori.citrep.usecases

import com.tonyakitori.citrep.data.repositories.AccountRepository
import com.tonyakitori.citrep.data.repositories.PostCollectionRepository
import com.tonyakitori.citrep.data.source.remote.FileId
import com.tonyakitori.citrep.domain.entities.PostEntity
import com.tonyakitori.citrep.domain.exceptions.PostExceptions
import com.tonyakitori.citrep.domain.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SavePostInDBUseCase(
    private val postCollectionRepository: PostCollectionRepository,
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(comment: String, fileIds: List<FileId>) = flow {
        emit(Response.Loading)

        var userId = ""
        var userName = ""

        accountRepository.getAccount().collect {
            when (it) {
                is Response.Error -> throw PostExceptions.UserNotFound()
                is Response.Success -> {
                    userId = it.data.userId
                    userName = it.data.name
                }
                else -> {}
            }
        }

        val getSecureIndex: (index: Int) -> String = { index: Int ->

            if (fileIds.lastIndex >= index) fileIds[index] else ""
        }

        val postEntity = PostEntity(
            userId = userId,
            userName = userName,
            comment = comment,
            image1FileId = getSecureIndex(0),
            image2FileId = getSecureIndex(1),
            image3FileId = getSecureIndex(2)
        )

        val response = postCollectionRepository.savePost(postEntity)

        emit(Response.Success(response))

    }.catch { exception ->
        emit(Response.Error(exception))
    }.flowOn(Dispatchers.IO)

}