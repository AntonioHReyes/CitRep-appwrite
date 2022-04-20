package com.tonyakitori.citrep.framework.data.remote

import com.tonyakitori.citrep.data.source.remote.AvatarDataSource
import io.appwrite.Client
import io.appwrite.services.Avatars

class AvatarAppWrite(private val appWriteClient: Client) : AvatarDataSource {

    override suspend fun getAvatarName(): ByteArray {
        val avatars = Avatars(appWriteClient)

        return avatars.getInitials()
    }


}