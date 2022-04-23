package com.tonyakitori.citrep.domain.exceptions

sealed class StorageExceptions(message: String? = null) : Exception(message) {

    class MaxFilesLoaded(message: String = "Max files loaded") : StorageExceptions(message)
    class UploadEvidencesError(message: String = "Error in load evidences") :
        StorageExceptions(message)

}
