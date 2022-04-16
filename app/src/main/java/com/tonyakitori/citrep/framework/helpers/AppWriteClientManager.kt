package com.tonyakitori.citrep.framework.helpers

import android.content.Context
import io.appwrite.Client


class AppWriteClientManager(context: Context) {

    private var client: Client = Client(context)
        .setEndpoint("http://192.168.1.72:4003/v1")
        .setProject("625b298d0c84745a378f")
        .setSelfSigned(status = true)

}