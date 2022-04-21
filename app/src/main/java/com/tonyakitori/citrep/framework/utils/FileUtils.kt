package com.tonyakitori.citrep.framework.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val cursor = query(uri, null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: 1)
    }

    return name
}
