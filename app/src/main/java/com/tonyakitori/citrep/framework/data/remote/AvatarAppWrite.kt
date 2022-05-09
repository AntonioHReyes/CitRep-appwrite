package com.tonyakitori.citrep.framework.data.remote

import com.tonyakitori.citrep.data.source.remote.AvatarDataSource
import io.appwrite.Client
import io.appwrite.services.Avatars

class AvatarAppWrite(private val appWriteClient: Client) : AvatarDataSource {

    private val avatars by lazy { Avatars(appWriteClient) }

    override suspend fun getAvatarName(name: String?): ByteArray {

        return avatars.getInitials(name)
    }


}