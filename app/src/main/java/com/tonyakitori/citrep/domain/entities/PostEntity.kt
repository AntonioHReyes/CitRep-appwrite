package com.tonyakitori.citrep.domain.entities

import com.tonyakitori.citrep.data.source.remote.DocumentId
import java.util.*

data class PostEntity(
    val id: DocumentId? = null,

    val userId: String,
    val userName: String,
    val comment: String = "",
    var image1FileId: String = "",
    var image2FileId: String = "",
    var image3FileId: String = "",
    val date: Long = Date().time,
    val enabled: Boolean = true,

    var user: UserPostEntity? = null,
    var image1ByteArray: ByteArray? = null, //TODO: Improve this
    var image2ByteArray: ByteArray? = null,
    var image3ByteArray: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as PostEntity

        return this.id == other.id
    }

    override fun hashCode(): Int {
        var result = image1ByteArray.contentHashCode()
        result = 31 * result + image2ByteArray.contentHashCode()
        result = 31 * result + image3ByteArray.contentHashCode()
        return result
    }
}