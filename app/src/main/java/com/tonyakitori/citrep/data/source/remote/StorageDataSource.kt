package com.tonyakitori.citrep.data.source.remote

import android.net.Uri

typealias FileId = String

interface StorageDataSource {

    suspend fun uploadImage(uri: Uri): FileId

}