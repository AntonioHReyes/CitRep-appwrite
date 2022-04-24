package com.tonyakitori.citrep.framework.domain

import com.google.gson.annotations.SerializedName
import com.tonyakitori.citrep.domain.entities.PostEntity

data class PostAppWrite(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("comment")
    val comment: String = "",
    @SerializedName("image1")
    val image1FileId: String = "",
    @SerializedName("image2")
    val image2FileId: String = "",
    @SerializedName("image3")
    val image3FileId: String = "",
    @SerializedName("enabled")
    val enabled: Boolean = true
)

fun PostEntity.toPostAppWrite() = PostAppWrite(
    userId, comment, image1FileId, image2FileId, image3FileId, enabled
)