package com.tonyakitori.citrep.data.repositories.impl

import com.tonyakitori.citrep.data.repositories.AvatarRepository
import com.tonyakitori.citrep.data.source.remote.AvatarDataSource
import com.tonyakitori.citrep.domain.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AvatarRepositoryImpl(private val avatarDataSource: AvatarDataSource) : AvatarRepository {

    override suspend fun getAvatar() = flow {
        emit(Response.Loading)
        val avatar = avatarDataSource.getAvatarName()
        emit(Response.Success(avatar))
    }.catch { error ->
        emit(Response.Error(error))
    }.flowOn(Dispatchers.IO)

}