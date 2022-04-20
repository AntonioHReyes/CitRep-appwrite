package com.tonyakitori.citrep.data.repositories

import com.tonyakitori.citrep.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface AvatarRepository {

    suspend fun getAvatar(): Flow<Response<ByteArray>>

}