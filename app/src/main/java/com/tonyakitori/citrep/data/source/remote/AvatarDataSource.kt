package com.tonyakitori.citrep.data.source.remote

interface AvatarDataSource {

    suspend fun getAvatarName(name: String? = null): ByteArray
}