package com.tonyakitori.citrep.framework.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toUri
import com.tonyakitori.citrep.framework.utils.createErrorLog
import com.tonyakitori.citrep.framework.utils.createInfoLog
import com.tonyakitori.citrep.framework.utils.getFileName
import java.io.*

class FileManagementService(
    private val context: Context,
    private val mainPath: String = MAIN_PATH,
    private val filePlace: FilePlace = FilePlace.FILES
) {

    fun saveFile(file: File): Uri? = handleException<Uri?> {
        val destinationFile = getDestinationFile()

        FileInputStream(file).copyTo(FileOutputStream(destinationFile))

        return destinationFile?.toUri()
    }

    fun saveUriAsFile(uri: Uri, isFromAndroidManagement: Boolean = false): Uri? =
        handleException<Uri?> {

            val inputStream = if (isFromAndroidManagement) {
                val parcelFileDescriptor =
                    context.contentResolver.openFileDescriptor(uri, "r", null)
                FileInputStream(parcelFileDescriptor?.fileDescriptor)
            } else {
                FileInputStream(uri.path?.let { File(it) })
            }

            val destinationFile =
                File(getDestinationFile(), context.contentResolver.getFileName(uri))
            val outPutStream = FileOutputStream(destinationFile)
            inputStream.copyTo(outPutStream)

            return destinationFile.toUri()
        }

    fun saveBitmapAsFile(bitmap: Bitmap): Uri? = handleException<Uri?> {
        val destinationFile = getDestinationFile()

        val out = FileOutputStream(destinationFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.flush()
        out.close()

        return destinationFile?.toUri()
    }

    fun getFile(uri: Uri): File? = handleException<File?> {
        return uri.path?.let {
            val file = File(it)

            if (file.exists()) file else null
        }
    }

    fun getFile(fileName: String, path: String? = getDestinationFile()?.absolutePath): File? =
        handleException<File?> {
            val file = File(path, fileName)

            return if (file.exists()) file else null
        }

    fun getFileUri(fileName: String, path: String? = getDestinationFile()?.absolutePath): Uri? =
        handleException<Uri?> {
            return getFile(fileName, path)?.toUri()
        }

    fun getFileAsBitmap(
        fileName: String,
        path: String? = getDestinationFile()?.absolutePath
    ): Bitmap? = handleException<Bitmap?> {
        val file = File(path, fileName)
        return BitmapFactory.decodeStream(FileInputStream(file))
    }

    fun getFileAsBitmap(uri: Uri): Bitmap? = handleException<Bitmap?> {
        val file = uri.path?.let { File(it) }
        return BitmapFactory.decodeStream(FileInputStream(file))
    }

    private fun getDestinationFile(): File? = handleException<File?> {
        val outSourceFileDestination = File(getPlaceToSaveFiles(), mainPath)

        outSourceFileDestination.exists().createErrorLog(TAG)

        "Creating path …".createInfoLog(TAG)

        var success = true
        if (!outSourceFileDestination.exists()) {
            success = outSourceFileDestination.mkdirs()
        }

        if (!success) {
            "Error in creation of Path".createErrorLog(TAG)
            throw FileManagementServiceException.CreationPathException("Error in creation of Path")
        }

        return outSourceFileDestination
    }

    private fun getPlaceToSaveFiles() = when (filePlace) {
        FilePlace.CACHE -> context.cacheDir
        FilePlace.FILES -> context.filesDir
    }

    private inline fun <T> handleException(block: () -> T): T? {
        return try {
            block()
        } catch (e: FileNotFoundException) {
            e.createErrorLog(TAG)
            null
        } catch (e: IOException) {
            e.createErrorLog(TAG)
            null
        } catch (e: Exception) {
            e.createErrorLog(TAG)
            null
        }
    }

    enum class FilePlace {
        CACHE, FILES
    }

    sealed class FileManagementServiceException(errorMessage: String?) : Exception(errorMessage) {

        class CreationPathException(message: String?) : FileManagementServiceException(message)

    }

    companion object {
        const val TAG = "FileManagerService"
        const val MAIN_PATH = "CitRep"
    }
}